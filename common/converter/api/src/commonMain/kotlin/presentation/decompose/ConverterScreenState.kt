package presentation.decompose

import domain.model.CurrencyEntity

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
    val usdRate: Double
) {
    companion object {
        fun stubFrom(): Currency = Currency(
            currencyCode = "RUB",
            currencyName = "Russian Rouble",
            flagImageUrl = "img",
            usdRate = 0.0
        )

        fun stubTo(): Currency = Currency(
            currencyCode = "USD",
            currencyName = "US Dollar",
            flagImageUrl = "stub",
            usdRate = 0.0
        )
    }
}

fun CurrencyEntity.toConverterCurrency(): Currency = Currency(
    currencyCode, currencyName, flagImageUrl, usdRate
)