package presentation.decompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.model.CurrencyListScreenState
import presentation.model.CurrencyListScreenState.LoadingStatus
import presentation.model.SortOption
import utils.Log
import utils.SnackbarAction
import utils.SnackbarController
import utils.SnackbarEvent

@OptIn(FlowPreview::class)
class CurrencyListComponentImpl(
    componentContext: ComponentContext,
    private val onCurrencySelected: (CurrencyEntity) -> Unit,
    private val onBackClick: () -> Unit,
    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
) : CurrencyListComponent, ComponentContext by componentContext, KoinComponent {

    private val newRepo: CurrenciesRepositoryNew by inject()

    override var searchText: String by mutableStateOf("")

    private val _screenState = MutableStateFlow(CurrencyListScreenState())
    override val screenState: StateFlow<CurrencyListScreenState> = _screenState
        .onStart { fetchCurrencies() }
        .map {
            it.copy(consumableData = it.data.filteredAndSorted(it.sortOption))
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CurrencyListScreenState()
        )

    init {
        lifecycle.doOnDestroy { componentScope.coroutineContext.cancelChildren() }
        componentScope.launch {
            snapshotFlow { searchText }.debounce(500).collectLatest {
                _screenState.update { it.copy(consumableData = it.data.filteredAndSorted(it.sortOption)) }
            }
        }
    }

    override fun changeSearchText(searchText: String) {
        this.searchText = searchText
    }

    override fun refreshCurrencies() {
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        componentScope.launch {
            newRepo.getUpdatedCurrencies().handleResponse()
            _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) }
        }
    }

    private suspend fun fetchCurrencies() {
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        newRepo.getCurrencies().handleResponse()
        _screenState.update { it.copy(loadingStatus = LoadingStatus.Idle) }
    }

    override fun onCurrencyClick(currency: CurrencyEntity) = onCurrencySelected.invoke(currency)

    override fun onBackClick() = onBackClick.invoke()

    override fun changeSortOrder(newSortOption: SortOption) {
        _screenState.update { it.copy(sortOption = newSortOption) }
    }

    private suspend fun Response<List<CurrencyEntity>>.handleResponse() = when (this) {
        is Response.Loading -> _screenState.update { it.copy(loadingStatus = LoadingStatus.Loading) }
        is Response.Success -> _screenState.update { it.copy(data = data) }
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