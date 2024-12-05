package presentation.model

data class CurrencyUiModel(
    val currencyCode: String,
    val currencyName: String,
    val flagImageUrl: String,
    val usdRate: Double,
    val fetchDate: String
)