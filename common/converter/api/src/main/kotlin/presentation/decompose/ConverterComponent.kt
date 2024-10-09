package presentation.decompose

import kotlinx.coroutines.flow.StateFlow

interface ConverterComponent {
    val screenState: StateFlow<ConverterScreenState>
    fun changeFromCurrency()
    fun changeToCurrency()
    fun changeFromAmount(fromAmount: Double)
}