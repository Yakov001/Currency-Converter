import domain.ConversionUseCase
import presentation.decompose.Currency
import kotlin.test.Test
import kotlin.test.assertEquals

class ConversionUseCaseTest {

    private val useCase = ConversionUseCase()

    private val rub = Currency.stubFrom().copy(usdRate = 0.010429639)
    private val usd = Currency.stubTo().copy(usdRate = 1.0)
    private val eur = Currency.stubTo().copy(currencyCode = "EUR", currencyName = "Euro", usdRate = 1.08753394)

    @Test
    fun correctly_convert_RUB_to_USD() {
        val result = useCase.calculateToAmount(
            fromAmount = 1.0,
            fromCurrency = rub,
            toCurrency = usd
        )
        assertEquals(expected = 0.010429639, actual = result)
    }

    @Test
    fun correctly_convert_RUB_to_EUR() {
        val result = useCase.calculateToAmount(
            fromAmount = 1.0,
            fromCurrency = rub,
            toCurrency = eur
        )
        assertEquals(
            expected = 0.0095901734.toString().take(8).toDouble(),
            actual = result.toString().take(8).toDouble()
        )
    }
}