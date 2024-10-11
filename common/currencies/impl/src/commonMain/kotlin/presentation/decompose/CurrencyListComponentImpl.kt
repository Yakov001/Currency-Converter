package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import data.CoinRepository
import data.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.decompose.CurrencyListScreenState.LoadingStatus
import utils.SnackbarAction
import utils.SnackbarController
import utils.SnackbarEvent

class CurrencyListComponentImpl(
    componentContext: ComponentContext,
    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : CurrencyListComponent, ComponentContext by componentContext, KoinComponent {

    private val repo: CoinRepository by inject()

    private val _screenState: MutableStateFlow<CurrencyListScreenState> =
        MutableStateFlow(CurrencyListScreenState())
    override val screenState: StateFlow<CurrencyListScreenState> = _screenState
        .onStart { fetchCurrencies() }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CurrencyListScreenState()
        )

    override fun refreshCurrencies() {
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        componentScope.launch {
            repo.getCurrencies()
                .onCompletion { _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) } }
                .collectLatest { response ->
                    when (response) {
                        is Response.Success -> {
                            _screenState.update { it.copy(data = response.data) }
                        }

                        is Response.Loading -> {
                            _screenState.update {
                                it.copy(
                                    loadingStatus = LoadingStatus.Loading
                                )
                            }
                        }

                        is Response.Failure -> {
                            _screenState.update {
                                it.copy(loadingStatus = LoadingStatus.Idle)
                            }
                            SnackbarController.sendEvent(
                                SnackbarEvent(
                                    message = response.message,
                                    action = SnackbarAction(
                                        name = "Retry",
                                        action = {
                                            fetchCurrencies()
                                        }
                                    )
                                )
                            )
                        }
                    }
                }
        }
    }
}