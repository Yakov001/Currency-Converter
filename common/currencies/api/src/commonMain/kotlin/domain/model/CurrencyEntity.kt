package domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CurrencyEntity(
    val currencyCode: String,
    val currencyName: String,
    val countryCode: String,
    val currencySymbol: String,
    val flagImageUrl: String,
    val usdRate : Double,
    val fetchTimeInstant: Instant = Clock.System.now()
)