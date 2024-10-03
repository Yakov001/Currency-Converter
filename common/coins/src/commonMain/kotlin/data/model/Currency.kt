package data.model

data class Currency(
    val currencyCode: String,
    val countryCode: String,
    val currencySymbol: String,
    val flagImageUrl: String,
    val usdRate : Double
)