package domain

import presentation.decompose.Currency

class ConversionUseCase {

    fun calculateToAmount(
        fromAmount: Double,
        fromCurrency: Currency,
        toCurrency: Currency
    ) : Double {
        val dollars: Double = fromCurrency.usdRate * fromAmount
        val toAmount: Double = dollars / toCurrency.usdRate
        return toAmount
    }

}