package presentation.decompose

import data.model.Currency
import kotlinx.coroutines.flow.StateFlow

interface CurrencyListComponent {
    val screenState: StateFlow<CurrencyListScreenState>
    fun refreshCurrencies()
    fun onCurrencyClick(currency: Currency)
}