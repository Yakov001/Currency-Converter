package utils

import data.model.CurrencyDto
import domain.model.CurrencyEntity

fun CurrencyDto.toEntity() = CurrencyEntity(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    fromUsd = usdRate,
    fetchTimeInstant = fetchTimeInstant
)

fun CurrencyEntity.toDto() = CurrencyDto(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    usdRate = fromUsd,
    fetchTimeInstant = fetchTimeInstant
)