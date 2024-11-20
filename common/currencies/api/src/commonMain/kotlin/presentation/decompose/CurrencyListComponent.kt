package presentation.decompose

import data.model.CurrencyDto
import kotlinx.coroutines.flow.StateFlow

interface CurrencyListComponent {
    val screenState: StateFlow<CurrencyListScreenState>
    fun searchByName(searchText: String)
    fun refreshCurrencies()
    fun onCurrencyClick(currency: CurrencyDto)

    fun List<CurrencyDto>?.sortedBySearchText(): List<CurrencyDto>? = this?.filter { cur ->
        val searchText = screenState.value.searchText
        if (searchText.isBlank()) true
        else cur.currencyName.contains(other = searchText, ignoreCase = true)
                || cur.currencyCode.contains(other = searchText, ignoreCase = true)
    }
}