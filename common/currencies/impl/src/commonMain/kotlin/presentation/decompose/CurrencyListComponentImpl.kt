package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import data.Response
import data.repository.CurrenciesRepositoryNew
import domain.model.CurrencyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.model.CurrencyListScreenState
import presentation.model.CurrencyListScreenState.LoadingStatus
import presentation.model.SortOption
import utils.SnackbarAction
import utils.SnackbarController
import utils.SnackbarEvent

@OptIn(FlowPreview::class)
class CurrencyListComponentImpl(
    componentContext: ComponentContext,
    private val onCurrencySelected: (CurrencyEntity) -> Unit,
    private val onBackClick: () -> Unit,
    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : CurrencyListComponent, ComponentContext by componentContext, KoinComponent {

    private val newRepo: CurrenciesRepositoryNew by inject()

    private val _screenState = MutableStateFlow(CurrencyListScreenState())
    override val screenState: StateFlow<CurrencyListScreenState> = _screenState
        .onStart { fetchCurrencies() }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CurrencyListScreenState()
        )

    init {
        lifecycle.doOnDestroy { componentScope.coroutineContext.cancelChildren() }

        CoroutineScope(Dispatchers.Default).launch {
            _screenState
                .distinctUntilChanged { old, new -> old.searchText == new.searchText }
                .debounce(500)
                .collectLatest {
                    sortCurrenciesByName()
                }
        }
    }

    override fun searchByName(searchText: String) {
        _screenState.update { it.copy(searchText = searchText) }
    }

    override fun refreshCurrencies() {
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        componentScope.launch {
            newRepo.getUpdatedCurrencies().handleResponse()
            _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) }
        }
    }

    private fun fetchCurrencies() {
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        componentScope.launch {
            newRepo.getCurrencies().handleResponse()
            _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) }
        }
    }

    override fun onCurrencyClick(currency: CurrencyEntity) = onCurrencySelected.invoke(currency)

    override fun onBackClick() = onBackClick.invoke()

    override fun changeSortOrder(newSortOption: SortOption) {
        _screenState.update { it.copy(sortOption = newSortOption) }
    }

    private fun sortCurrenciesByName() {
        _screenState.update { it.copy(sortedData = it.data.sortedBySearchText()) }
    }

    private suspend fun Response<List<CurrencyEntity>>.handleResponse() = when (this) {
        is Response.Loading -> _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        is Response.Success -> {
            _screenState.update { it.copy(data = data, sortedData = data) }
            sortCurrenciesByName()
        }
        is Response.Failure -> {
            _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) }
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = message,
                    action = SnackbarAction("Try again") { fetchCurrencies() }
                )
            )
        }
    }
}