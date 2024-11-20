package data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    val currencyCode: String,
    val currencyName: String,
    val countryCode: String,
    val currencySymbol: String,
    val flagImageUrl: String,
    val usdRate : Double
)
