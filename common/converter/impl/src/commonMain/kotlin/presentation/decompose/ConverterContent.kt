package presentation.decompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import presentation.composables.CurrencySlot
import presentation.composables.CurrencySlotTextView

@Composable
fun ConverterContent(component: ConverterComponent) {

    val state by component.screenState.collectAsState()
    val slot by component.currencyListSlot.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CurrencySlot(
                flagImageUrl = state.fromCurrency.flagImageUrl,
                currencyName = state.fromCurrency.currencyName,
                currencyCode = state.fromCurrency.currencyCode,
                onClick = component::changeFromCurrency,
                textField = {
                    CurrencySlotTextView(
                        amountState = state.fromAmountState,
                        onTextChange = component::changeFromState
                    )
                }
            )
            CurrencySlot(
                flagImageUrl = state.toCurrency.flagImageUrl,
                currencyName = state.toCurrency.currencyName,
                currencyCode = state.toCurrency.currencyCode,
                onClick = component::changeToCurrency,
                textField = {
                    CurrencySlotTextView(
                        amountState = state.toAmountState,
                        onTextChange = { },
                        enabled = false
                    )
                }
            )
        }

        slot.child?.instance?.let { currencyListComponent ->
            Surface {
                CurrencyListContent(
                    component = currencyListComponent
                )
            }
        }
    }
}