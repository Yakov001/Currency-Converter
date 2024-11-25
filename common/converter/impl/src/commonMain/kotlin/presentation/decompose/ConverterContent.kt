package presentation.decompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import presentation.composables.BackgroundLayer
import presentation.composables.ConverterLayer

@Composable
fun ConverterContent(component: ConverterComponent) {

    val state by component.screenState.collectAsState()
    val slot by component.currencyListSlot.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(
                PaddingValues(bottom = 90.dp)
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.ime)
            .padding(bottom = 90.dp)
    ) {
        BackgroundLayer()
        ConverterLayer(
            state = state,
            component = component
        )

        slot.child?.instance?.let { currencyListComponent ->
            Surface {
                CurrencyListContent(
                    component = currencyListComponent
                )
            }
        }
    }
}