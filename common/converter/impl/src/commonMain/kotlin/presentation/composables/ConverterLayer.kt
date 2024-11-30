package presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import presentation.decompose.ConverterComponent
import presentation.decompose.ConverterScreenState

@Composable
fun ConverterLayer(
    state: ConverterScreenState,
    component: ConverterComponent
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ConverterBlock(state, component)
        Spacer(
            modifier = Modifier
                .consumeWindowInsets(
                    PaddingValues(bottom = 90.dp)
                )
                .padding(bottom = 90.dp)
                .windowInsetsPadding(WindowInsets.ime)
        )
    }
}

@Composable
fun ConverterBlock(
    state: ConverterScreenState,
    component: ConverterComponent
) {
    val shape = MaterialTheme.shapes.medium

    ElevatedCard(
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 12.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                val focusManager = LocalFocusManager.current

                CurrencySlot(
                    flagImageUrl = state.fromCurrency.flagImageUrl,
                    currencyName = state.fromCurrency.currencyName,
                    currencyCode = state.fromCurrency.currencyCode,
                    fetchDate = state.fromCurrency.fetchDate,
                    onClick = {
                        focusManager.clearFocus()
                        component.changeFromCurrency()
                    },
                    textField = {
                        CurrencySlotTextField(
                            amountState = state.fromAmountState,
                            onTextChange = component::changeFromState
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                CurrencySlot(
                    flagImageUrl = state.toCurrency.flagImageUrl,
                    currencyName = state.toCurrency.currencyName,
                    currencyCode = state.toCurrency.currencyCode,
                    fetchDate = state.fromCurrency.fetchDate,
                    onClick = {
                        focusManager.clearFocus()
                        component.changeToCurrency()
                    },
                    textField = {
                        CurrencySlotTextField(
                            amountState = state.toAmountState,
                            onTextChange = { },
                            enabled = false
                        )
                    }
                )
            }
            Box(
                modifier = Modifier.padding(start = 32.dp)
            ) {
                ButtonSwitchCurrencies(
                    onClick = component::switchCurrencies
                )
            }
        }
    }
}