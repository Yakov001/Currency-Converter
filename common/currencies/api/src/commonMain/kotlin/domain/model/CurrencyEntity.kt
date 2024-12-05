package domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CurrencyEntity(
    val currencyCode: String,
    val currencyName: String,
    val flagImageUrl: String,
    val fromUsd: Double,
    val fetchTimeInstant: Instant = Clock.System.now()
) {
    companion object
}