package presentation.util

import domain.model.CurrencyEntity

fun CurrencyEntity.Companion.defaultFrom(): CurrencyEntity = CurrencyEntity(
    currencyCode = "RUB",
    currencyName = "Russian Rouble",
    flagImageUrl = "img",
    usdRate = 0.0
)

fun CurrencyEntity.Companion.defaultTo(): CurrencyEntity = CurrencyEntity(
    currencyCode = "USD",
    currencyName = "US Dollar",
    flagImageUrl = "stub",
    usdRate = 0.0
)