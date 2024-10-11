package presentation.decompose

import data.model.Currency

data class CurrencyListScreenState(
    val data: List<Currency>? = null,
    val loadingStatus: LoadingStatus = LoadingStatus.Idle
) {
    sealed interface LoadingStatus {
        data object Idle : LoadingStatus
        data object Loading : LoadingStatus
    }
}