package presentation.util

import domain.model.CurrencyEntity

fun CurrencyEntity.Companion.defaultFrom(): CurrencyEntity = CurrencyEntity(
    currencyCode = "USD",
    currencyName = "US Dollar",
    flagImageUrl = "stub",
    fromUsd = 0.0
)

fun CurrencyEntity.Companion.defaultTo(): CurrencyEntity = CurrencyEntity(
    currencyCode = "RUB",
    currencyName = "Russian Rouble",
    flagImageUrl = "img",
    fromUsd = 0.0
)