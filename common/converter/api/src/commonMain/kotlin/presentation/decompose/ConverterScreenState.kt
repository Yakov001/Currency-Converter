package presentation.decompose

import domain.model.CurrencyEntity
import utils.toLocalDateTimeText

data class ConverterScreenState(
    val fromCurrency: Currency = Currency.stubFrom(),
    val toCurrency: Currency = Currency.stubTo(),

    val fromAmountState: TextFieldState = TextFieldState(),
    val toAmountState: TextFieldState = TextFieldState()
)

data class Currency(
    val currencyCode: String,
    val currencyName: String,
    val flagImageUrl: String,
    val usdRate: Double,
    val fetchDate: String
) {
    companion object {
        fun stubFrom(): Currency = Currency(
            currencyCode = "RUB",
            currencyName = "Russian Rouble",
            flagImageUrl = "img",
            usdRate = 0.0,
            fetchDate = "2 November 2024"
        )

        fun stubTo(): Currency = Currency(
            currencyCode = "USD",
            currencyName = "US Dollar",
            flagImageUrl = "stub",
            usdRate = 0.0,
            fetchDate = "2 November 2024"
        )
    }
}

fun CurrencyEntity.toConverterCurrency(): Currency = Currency(
    currencyCode = currencyCode,
    currencyName = currencyName,
    flagImageUrl = flagImageUrl,
    usdRate = usdRate,
    fetchDate = fetchTimeInstant.toLocalDateTimeText()
)