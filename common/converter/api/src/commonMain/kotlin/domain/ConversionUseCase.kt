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
        val dollars: Double = fromAmount / fromCurrency.fromUsd
        val toAmount: Double = dollars * toCurrency.fromUsd

        return toAmount.roundToNDecimals()
    }

    companion object {
        fun Double.roundToNDecimals(decimalPlaces: Int = 3): Double {
            val factor = 10.0.pow(decimalPlaces)
            val roundedValue = round(this * factor) / factor
            return roundedValue
        }
    }

}