package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import data.CoinRepository
import data.Response
import data.model.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.Log

interface CurrencyListComponent {
    val currencies : StateFlow<List<Currency>>
}

class CurrencyListComponentImpl(
    componentContext: ComponentContext,
) : CurrencyListComponent, ComponentContext by componentContext, KoinComponent {

    private val repo : CoinRepository by inject()

    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _currencies: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    override val currencies: StateFlow<List<Currency>> = _currencies
        .onStart { fetchCurrencies() }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun fetchCurrencies() {
        componentScope.launch {
            repo.getCurrencies().collect { new ->
                Log.d("collect: $new")
                if (new is Response.Success) _currencies.update { new.data }
            }
        }
    }
}