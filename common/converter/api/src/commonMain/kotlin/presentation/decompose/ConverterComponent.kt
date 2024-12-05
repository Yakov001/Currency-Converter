package presentation.decompose

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import presentation.model.ConverterScreenState
import presentation.model.TextFieldState

interface ConverterComponent {
    val currencyListSlot: Value<ChildSlot<*, CurrencyListComponent>>
    val screenState: StateFlow<ConverterScreenState>
    fun recalculateToAmount()
    fun changeFromCurrency()
    fun changeToCurrency()
    fun changeFromState(state: TextFieldState)
    fun switchCurrencies()
}