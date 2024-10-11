package presentation.decompose

import kotlinx.coroutines.flow.StateFlow

interface CurrencyListComponent {
    val screenState: StateFlow<CurrencyListScreenState>
    fun refreshCurrencies()
}