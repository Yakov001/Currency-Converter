package utils

import data.model.CurrencyDto
import presentation.model.CurrencyEntity

fun CurrencyDto.toEntity() = CurrencyEntity(
    currencyCode = currencyCode,
    currencyName = currencyName,
    countryCode = countryCode,
    currencySymbol = currencySymbol,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate,
)

fun CurrencyEntity.toDto() = CurrencyDto(
    currencyCode = currencyCode,
    currencyName = currencyName,
    countryCode = countryCode,
    currencySymbol = currencySymbol,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate,
)