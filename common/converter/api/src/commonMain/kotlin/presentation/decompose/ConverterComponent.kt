package presentation.decompose

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow

interface ConverterComponent {
    val currencyListSlot: Value<ChildSlot<*, CurrencyListComponent>>
    val screenState: StateFlow<ConverterScreenState>
    fun changeFromCurrency()
    fun changeToCurrency()
    fun changeFromAmount(text: String)
}