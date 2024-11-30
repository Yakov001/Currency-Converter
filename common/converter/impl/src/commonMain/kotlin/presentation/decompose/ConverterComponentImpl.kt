package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import data.Response
import data.repository.CurrenciesRepository
import domain.ConversionUseCase
import domain.model.CurrencyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConverterComponentImpl(
    componentContext: ComponentContext,
    private val componentScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : ConverterComponent, ComponentContext by componentContext, KoinComponent {

    private val repo: CurrenciesRepository by inject()

    private val _screenState: MutableStateFlow<ConverterScreenState> = MutableStateFlow(ConverterScreenState())
    override val screenState: StateFlow<ConverterScreenState> = _screenState
        .onStart {
            // Get the first successful response (likely from local storage)
            repo.getCurrencies()
                .filterIsInstance<Response.Success<List<CurrencyEntity>>>()
                .take(1)
                .collect { currencies ->
                    _screenState.update { state ->
                        state.copy(
                            fromCurrency = currencies.data.findFromCurrency(),
                            toCurrency = currencies.data.findToCurrency()
                        )
                    }
                }
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ConverterScreenState()
        )

    private val slotNavigation = SlotNavigation<CurrenciesConfig>()
    override val currencyListSlot: Value<ChildSlot<*, CurrencyListComponent>> = childSlot(
        source = slotNavigation,
        serializer = CurrenciesConfig.serializer(), // Or null to disable navigation state saving
        handleBackButton = true, // Close the dialog on back button press
    ) { config, childComponentContext ->
        CurrencyListComponentImpl(
            componentContext = childComponentContext,
            onCurrencySelected = { currencySelected ->
                slotNavigation.dismiss()
                _screenState.update {
                    when (config.changedCurrenciesConfig) {
                        CurrenciesConfig.ChangedCurrency.From -> {
                            it.copy(fromCurrency = currencySelected.toUiModel())
                        }
                        CurrenciesConfig.ChangedCurrency.To -> {
                            it.copy(toCurrency = currencySelected.toUiModel())
                        }
                    }
                }
                recalculateToAmount()
            },
            onBackClick = slotNavigation::dismiss
        )
    }

    private val useCase : ConversionUseCase by inject()
    override fun recalculateToAmount() {
        _screenState.update { state ->
            val newAmount = useCase.calculateToAmount(
                fromAmount = state.fromAmountState.amount,
                fromCurrency = state.fromCurrency,
                toCurrency = state.toCurrency
            )
            state.copy(
                toAmountState = state.toAmountState.copy(newAmount)
            )
        }
    }

    override fun changeFromCurrency() {
        slotNavigation.activate(CurrenciesConfig(CurrenciesConfig.ChangedCurrency.From))
    }

    override fun changeToCurrency() {
        slotNavigation.activate(CurrenciesConfig(CurrenciesConfig.ChangedCurrency.To))
    }

    override fun switchCurrencies() {
        _screenState.update {
            it.copy(
                fromCurrency = it.toCurrency,
                toCurrency = it.fromCurrency,
                fromAmountState = it.fromAmountState.copy(caretPos = it.toAmountState.amountText.length),
                toAmountState = it.toAmountState.copy(caretPos = 0)
            )
        }
        recalculateToAmount()
    }

    override fun changeFromState(state: TextFieldState) {
        if (state.amountText.contains("-")) return
        state.amount.let { double ->
            if (double < 0 || double.toString().length > 10) return
            if (double == 0.0) _screenState.update { it.copy(fromAmountState = state.copy(caretPos = 1)) }
            else _screenState.update { it.copy(fromAmountState = state) }
        }
        recalculateToAmount()
    }

    companion object {
        private val defaultFrom = CurrencyUiModel.defaultFrom()
        private val defaultTo = CurrencyUiModel.defaultTo()

        private fun List<CurrencyEntity>.findFromCurrency() : CurrencyUiModel =
            find { it.currencyCode == defaultFrom.currencyCode }?.toUiModel() ?: defaultFrom
        private fun List<CurrencyEntity>.findToCurrency() : CurrencyUiModel =
            find { it.currencyCode == defaultTo.currencyCode }?.toUiModel() ?: defaultTo

    }

    @Serializable
    private data class CurrenciesConfig(
        val changedCurrenciesConfig: ChangedCurrency
    ) {
        @Serializable
        sealed interface ChangedCurrency {
            data object From : ChangedCurrency
            data object To : ChangedCurrency
        }
    }
}