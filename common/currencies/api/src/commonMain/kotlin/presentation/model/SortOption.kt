package presentation.model

sealed class SortOption(
    open val ascending: Boolean
) {

    companion object {
        val default: SortOption = CurrencyName()
    }

    data class CurrencyName(override val ascending: Boolean = true) : SortOption(ascending)
    data class CurrencyCode(override val ascending: Boolean = true) : SortOption(ascending)
}