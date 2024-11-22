package presentation.decompose

import domain.model.CurrencyEntity
import utils.toLocalDateTimeText

data class ConverterScreenState(
    val fromCurrency: CurrencyUiModel = CurrencyUiModel.defaultFrom(),
    val toCurrency: CurrencyUiModel = CurrencyUiModel.defaultTo(),

    val fromAmountState: TextFieldState = TextFieldState(),
    val toAmountState: TextFieldState = TextFieldState()
)

data class CurrencyUiModel(
    val currencyCode: String,
    val currencyName: String,
    val flagImageUrl: String,
    val usdRate: Double,
    val fetchDate: String
) {
    companion object {
        fun defaultFrom(): CurrencyUiModel = CurrencyUiModel(
            currencyCode = "RUB",
            currencyName = "Russian Rouble",
            flagImageUrl = "img",
            usdRate = 0.0,
            fetchDate = "2 November 2024"
        )

        fun defaultTo(): CurrencyUiModel = CurrencyUiModel(
            currencyCode = "USD",
            currencyName = "US Dollar",
            flagImageUrl = "stub",
            usdRate = 0.0,
            fetchDate = "2 November 2024"
        )
    }
}

fun CurrencyEntity.toUiModel(): CurrencyUiModel = CurrencyUiModel(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate,
    fetchDate = fetchTimeInstant.toLocalDateTimeText()
)