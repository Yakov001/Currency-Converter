package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class ConverterComponentImpl(
    componentContext: ComponentContext,
    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : ConverterComponent, ComponentContext by componentContext, KoinComponent {

    private val _screenState: MutableStateFlow<ConverterScreenState> = MutableStateFlow(ConverterScreenState())
    override val screenState: StateFlow<ConverterScreenState> = _screenState
        .onStart {  }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ConverterScreenState()
        )

    override val currencyListSlot: StateFlow<ChildSlot<*, String>> = TODO()

    override fun changeFromCurrency() {

    }

    override fun changeToCurrency() {

    }

    override fun changeFromAmount(text: String) {
        if (text.isBlank()) _screenState.update { it.copy(fromAmount = 0.0) }
        if (text.contains("-")) return
        text.toDoubleOrNull()?.let { double ->
            if (double < 0 || double.toString().length > 10) return
            _screenState.update { it.copy(fromAmount = double) }
        }
    }
}