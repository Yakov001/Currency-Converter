package presentation.decompose

import domain.model.CurrencyEntity

fun List<CurrencyEntity>?.filteredBySearch(searchText: String): List<CurrencyEntity>? =
    this?.filter { cur ->
        if (searchText.isBlank()) true
        else cur.currencyName.contains(other = searchText, ignoreCase = true)
                || cur.currencyCode.contains(other = searchText, ignoreCase = true)
    }