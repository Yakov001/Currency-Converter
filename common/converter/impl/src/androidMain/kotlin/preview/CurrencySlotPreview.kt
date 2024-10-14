package preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import presentation.composables.CurrencySlot
import presentation.composables.CurrencySlotTextView
import theme.ResonanseTheme

@Composable
@Preview
private fun CurrencySlotPreview() = ResonanseTheme {
    Surface {

        var text by remember { mutableStateOf("") }

        CurrencySlot(
            flagImageUrl = """https://flagcdn.com/w320/ru.png""",
            currencyName = "Russian Ruble",
            currencyCode = "RUB",
            onClick = {},
            textField = {
                CurrencySlotTextView(
                    text = text,
                    onTextChange = { text = it }
                )
            }
        )
    }
}