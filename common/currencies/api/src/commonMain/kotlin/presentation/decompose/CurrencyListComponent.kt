package presentation.decompose

import kotlinx.coroutines.flow.StateFlow
import domain.model.CurrencyEntity

interface CurrencyListComponent {
    val screenState: StateFlow<CurrencyListScreenState>
    fun searchByName(searchText: String)
    fun refreshCurrencies()
    fun onCurrencyClick(currency: CurrencyEntity)
    fun onBackClick()

    fun List<CurrencyEntity>?.sortedBySearchText(): List<CurrencyEntity>? = this?.filter { cur ->
        val searchText = screenState.value.searchText
        if (searchText.isBlank()) true
        else cur.currencyName.contains(other = searchText, ignoreCase = true)
                || cur.currencyCode.contains(other = searchText, ignoreCase = true)
    }
}