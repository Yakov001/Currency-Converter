package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import data.ktor.KtorCoinDataSource
import data.ktor.dto.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CurrencyListComponent {
    val currencies : StateFlow<List<Currency>>
}

class CurrencyListComponentImpl(
    componentContext: ComponentContext,
) : CurrencyListComponent, ComponentContext by componentContext, KoinComponent {

    private val dataSource : KtorCoinDataSource by inject()

    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _currencies: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    override val currencies: StateFlow<List<Currency>> = _currencies
        .onStart { fetchCurrencies() }
        .stateIn(
            componentScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun fetchCurrencies() {
        componentScope.launch {
            val currencies = dataSource.getCurrencies()
            _currencies.update { currencies }
        }
    }
}