package presentation.decompose

import data.model.Currency

data class CurrencyListScreenState(
    val data: List<Currency>? = null,
    val sortedData : List<Currency>? = data,
    val loadingStatus: LoadingStatus = LoadingStatus.Idle,
    val searchText : String = ""
) {
    sealed interface LoadingStatus {
        data object Idle : LoadingStatus
        data object Loading : LoadingStatus
    }
}