package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
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

    private val dialogNavigation = SlotNavigation<CurrenciesConfig>()
    override val currencyListSlot: Value<ChildSlot<*, CurrencyListComponent>> = childSlot(
        source = dialogNavigation,
        serializer = CurrenciesConfig.serializer(), // Or null to disable navigation state saving
        handleBackButton = true, // Close the dialog on back button press
    ) { config, childComponentContext ->
        CurrencyListComponentImpl(
            componentContext = childComponentContext,
            onCurrencySelected = { currencySelected ->
                dialogNavigation.dismiss()
                when (config.changedCurrenciesConfig) {
                    CurrenciesConfig.ChangedCurrency.From -> {
                        _screenState.update { it.copy(fromCurrency = currencySelected.toConverterCurrency()) }
                    }
                    CurrenciesConfig.ChangedCurrency.To -> {
                        _screenState.update { it.copy(toCurrency = currencySelected.toConverterCurrency()) }
                    }
                }
            }
        )
    }

    override fun changeFromCurrency() {
        dialogNavigation.activate(CurrenciesConfig(CurrenciesConfig.ChangedCurrency.From))
    }

    override fun changeToCurrency() {
        dialogNavigation.activate(CurrenciesConfig(CurrenciesConfig.ChangedCurrency.To))
    }

    override fun changeFromAmount(text: String) {
        if (text.isBlank()) _screenState.update { it.copy(fromAmount = 0.0) }
        if (text.contains("-")) return
        text.toDoubleOrNull()?.let { double ->
            if (double < 0 || double.toString().length > 10) return
            _screenState.update { it.copy(fromAmount = double) }
        }
    }

    @Serializable
    private data class CurrenciesConfig(
        val changedCurrenciesConfig: ChangedCurrency
    ) {
        sealed interface ChangedCurrency {
            data object From : ChangedCurrency
            data object To : ChangedCurrency
        }
    }
}