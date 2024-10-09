package presentation.decompose

data class Currency(
    val currencyCode: String,
    val currencyName: String,
    val flagImageUrl: String,
    val usdRate : Double
)

data class ConverterScreenState(
    val fromCurrency : Currency,
    val toCurrency : Currency,

    val fromAmount : Double = 0.0,
    val toAmount : Double = 0.0
)