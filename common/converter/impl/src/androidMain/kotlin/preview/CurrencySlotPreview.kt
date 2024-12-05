package preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.composables.ButtonSwitchCurrencies
import presentation.composables.CurrencySlot
import presentation.composables.CurrencySlotTextField
import presentation.model.TextFieldState
import theme.ResonanseTheme

@Composable
@Preview
private fun CurrencySlotPreview() = ResonanseTheme {
    Surface {

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CurrencySlot(
                flagImageUrl = """https://flagcdn.com/w320/ru.png""",
                currencyName = "Russian Ruble",
                currencyCode = "RUB",
                onClick = {},
                textField = {
                    CurrencySlotTextField(
                        amountState = TextFieldState(),
                        onTextChange = {  }
                    )
                }
            )
            ButtonSwitchCurrencies(
                onClick = {}
            )
            CurrencySlot(
                flagImageUrl = """https://flagcdn.com/w320/ru.png""",
                currencyName = "US Dollar",
                currencyCode = "USD",
                onClick = {},
                textField = {
                    CurrencySlotTextField(
                        amountState = TextFieldState(),
                        onTextChange = {  }
                    )
                }
            )

        }

    }
}