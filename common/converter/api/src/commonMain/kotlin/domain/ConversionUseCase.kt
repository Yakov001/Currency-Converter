package domain

import domain.model.CurrencyEntity
import kotlin.math.pow
import kotlin.math.round

class ConversionUseCase {

    fun calculateToAmount(
        fromAmount: Double,
        fromCurrency: CurrencyEntity,
        toCurrency: CurrencyEntity
    ) : Double {
        val dollars: Double = fromCurrency.usdRate * fromAmount
        val toAmount: Double = dollars / toCurrency.usdRate

        return toAmount.roundToNDecimals(3)
    }

    private fun Double.roundToNDecimals(decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces)
        val roundedValue = round(this * factor) / factor
        return roundedValue
    }

}