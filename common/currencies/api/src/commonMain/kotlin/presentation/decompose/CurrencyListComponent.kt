package presentation.decompose

import kotlinx.coroutines.flow.StateFlow
import domain.model.CurrencyEntity
import presentation.model.CurrencyListScreenState
import presentation.model.SortOption

interface CurrencyListComponent {
    var searchText: String
    val screenState: StateFlow<CurrencyListScreenState>
    fun changeSearchText(searchText: String)
    fun refreshCurrencies()
    fun onCurrencyClick(currency: CurrencyEntity)
    fun onBackClick()
    fun changeSortOrder(newSortOption: SortOption)

    fun List<CurrencyEntity>?.filteredAndSorted(sortOption: SortOption) : List<CurrencyEntity>? =
        filteredBySearch(searchText).sortedByOptions(sortOption)
}