package presentation.decompose

import data.model.CurrencyDto

data class CurrencyListScreenState(
    val data: List<CurrencyDto>? = null,
    val sortedData : List<CurrencyDto>? = data,
    val loadingStatus: LoadingStatus = LoadingStatus.Idle,
    val searchText : String = ""
) {
    sealed interface LoadingStatus {
        data object Idle : LoadingStatus
        data object Loading : LoadingStatus
    }
}