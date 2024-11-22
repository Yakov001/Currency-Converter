package domain

import presentation.decompose.CurrencyUiModel

class ConversionUseCase {

    fun calculateToAmount(
        fromAmount: Double,
        fromCurrency: CurrencyUiModel,
        toCurrency: CurrencyUiModel
    ) : Double {
        val dollars: Double = fromCurrency.usdRate * fromAmount
        val toAmount: Double = dollars / toCurrency.usdRate
        return toAmount
    }

}