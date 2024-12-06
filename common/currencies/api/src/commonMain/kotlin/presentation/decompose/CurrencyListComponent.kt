package presentation.decompose

import kotlinx.coroutines.flow.StateFlow
import domain.model.CurrencyEntity
import presentation.model.CurrencyListScreenState
import presentation.model.SortOption

interface CurrencyListComponent {
    val screenState: StateFlow<CurrencyListScreenState>
    fun searchByName(searchText: String)
    fun refreshCurrencies()
    fun onCurrencyClick(currency: CurrencyEntity)
    fun onBackClick()
    fun changeSortOrder(newSortOption: SortOption)

    fun List<CurrencyEntity>?.filteredBySearch(): List<CurrencyEntity>? = this?.filter { cur ->
        val searchText = screenState.value.searchText
        if (searchText.isBlank()) true
        else cur.currencyName.contains(other = searchText, ignoreCase = true)
                || cur.currencyCode.contains(other = searchText, ignoreCase = true)
    }

    fun List<CurrencyEntity>?.sortedByOptions() : List<CurrencyEntity>? = this?.sortedBy { cur ->
        when (screenState.value.sortOption) {
            is SortOption.CurrencyCode -> cur.currencyCode
            is SortOption.CurrencyName -> cur.currencyName
        }.let { if (screenState.value.sortOption.ascending) it else it.reversed() }
    }
}