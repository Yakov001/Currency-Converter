package presentation.decompose

import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow

interface ConverterComponent {
    val currencyListSlot: StateFlow<ChildSlot<*, String>>
    val screenState: StateFlow<ConverterScreenState>
    fun changeFromCurrency()
    fun changeToCurrency()
    fun changeFromAmount(text: String)
}