package presentation.util

import domain.model.CurrencyEntity
import presentation.model.CurrencyUiModel
import utils.toLocalDateTimeText

fun CurrencyEntity.toUiModel(): CurrencyUiModel = CurrencyUiModel(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate,
    fetchDate = fetchTimeInstant.toLocalDateTimeText()
)

fun CurrencyUiModel.toEntity(): CurrencyEntity = CurrencyEntity(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate
)