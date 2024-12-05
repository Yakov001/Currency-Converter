package presentation.util

import domain.model.CurrencyEntity
import presentation.model.CurrencyUiModel

fun CurrencyEntity.toUiModel(): CurrencyUiModel = CurrencyUiModel(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    fromUsd = fromUsd
)

fun CurrencyUiModel.toEntity(): CurrencyEntity = CurrencyEntity(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    fromUsd = fromUsd
)