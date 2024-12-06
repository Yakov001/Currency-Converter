package presentation.model

import domain.model.CurrencyEntity

data class CurrencyListScreenState(
    val data: List<CurrencyEntity>? = null,
    val sortedData : List<CurrencyEntity>? = data,
    val loadingStatus: LoadingStatus = LoadingStatus.Idle,
    val searchText : String = "",
    val sortOption: SortOption = SortOption.default
) {
    sealed interface LoadingStatus {
        data object Idle : LoadingStatus
        data object Loading : LoadingStatus
    }
}