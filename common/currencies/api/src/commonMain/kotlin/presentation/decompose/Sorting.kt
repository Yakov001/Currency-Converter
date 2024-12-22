package presentation.decompose

import domain.model.CurrencyEntity
import presentation.model.SortOption
import utils.Log

fun List<CurrencyEntity>?.sortedByOptions(sortOption: SortOption): List<CurrencyEntity>? {
    fun getStringToCompare(cur: CurrencyEntity): String = when(sortOption) {
        is SortOption.CurrencyCode -> cur.currencyCode
        is SortOption.CurrencyName -> cur.currencyName
    }
    return if (sortOption.ascending)
        this?.sortedBy { cur -> getStringToCompare(cur) }
    else
        this?.sortedByDescending { cur -> getStringToCompare(cur) }
}