import domain.ConversionUseCase
import domain.ConversionUseCase.Companion.roundToNDecimals
import domain.model.CurrencyEntity
import presentation.util.defaultFrom
import presentation.util.defaultTo
import kotlin.test.Test
import kotlin.test.assertEquals

class ConversionUseCaseTest {

    // TODO: test is useless now, need to consider if API gives to usd of from usd rate

    private val useCase = ConversionUseCase()

    private val rub = CurrencyEntity.defaultFrom().copy(fromUsd = 0.010429639)
    private val usd = CurrencyEntity.defaultTo().copy(fromUsd = 1.0)
    private val eur = CurrencyEntity.defaultTo().copy(fromUsd = 1.08753394)

    @Test
    fun correctly_convert_RUB_to_USD() {
        val result = useCase.calculateToAmount(
            fromAmount = 1.0,
            fromCurrency = rub,
            toCurrency = usd
        )
        assertEquals(expected = 0.010429639.roundToNDecimals(), actual = result)
    }

    @Test
    fun correctly_convert_RUB_to_EUR() {
        val result = useCase.calculateToAmount(
            fromAmount = 1.0,
            fromCurrency = rub,
            toCurrency = eur
        )
        assertEquals(
            expected = 0.0095901734.roundToNDecimals(),
            actual = result
        )
    }
}