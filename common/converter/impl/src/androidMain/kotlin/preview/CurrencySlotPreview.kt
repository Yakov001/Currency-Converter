package preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import presentation.composables.CurrencySlot
import presentation.composables.CurrencySlotTextView
import presentation.decompose.TextFieldState
import theme.ResonanseTheme

@Composable
@Preview
private fun CurrencySlotPreview() = ResonanseTheme {
    Surface {

        CurrencySlot(
            flagImageUrl = """https://flagcdn.com/w320/ru.png""",
            currencyName = "Russian Ruble",
            currencyCode = "RUB",
            onClick = {},
            textField = {
                CurrencySlotTextView(
                    amountState = TextFieldState(),
                    onTextChange = {  }
                )
            }
        )
    }
}