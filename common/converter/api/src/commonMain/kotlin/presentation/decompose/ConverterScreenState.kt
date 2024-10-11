package presentation.decompose

import kotlin.math.ceil
import kotlin.math.floor

data class ConverterScreenState(
    val fromCurrency: Currency = Currency.stubFrom(),
    val toCurrency: Currency = Currency.stubTo(),

    val fromAmount: Double = 0.0,
    val toAmount: Double = 0.0
) {
    val fromAmountText: String
        get() {
            return if (floor(fromAmount) == ceil(fromAmount))
                fromAmount.toInt().toString()
            else
                fromAmount.toString()
        }

    val toAmountText: String
        get() {
            return if (floor(toAmount) == ceil(toAmount))
                toAmount.toInt().toString()
            else
                toAmount.toString()
        }
}

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