package presentation.model

import domain.model.CurrencyEntity
import presentation.util.defaultFrom
import presentation.util.defaultTo
import presentation.util.toUiModel

data class ConverterScreenState(
    val fromCurrency: CurrencyUiModel = CurrencyEntity.defaultFrom().toUiModel(),
    val toCurrency: CurrencyUiModel = CurrencyEntity.defaultTo().toUiModel(),

    val fromAmountState: TextFieldState = TextFieldState(),
    val toAmountState: TextFieldState = TextFieldState()
)