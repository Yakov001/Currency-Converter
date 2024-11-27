package presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchCurrencyTextField(
    text : String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        modifier = modifier,
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "clear"
                    )
                }
            }
        }
    )
}