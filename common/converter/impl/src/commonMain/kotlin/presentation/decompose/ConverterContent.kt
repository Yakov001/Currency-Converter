package presentation.decompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import presentation.composables.screen_layers.BackgroundLayer
import presentation.composables.screen_layers.ConverterLayer
import presentation.composables.screen_layers.TopBarLayer

@Composable
fun ConverterContent(component: ConverterComponent) {

    val state by component.screenState.collectAsState()
    val slot by component.currencyListSlot.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundLayer()
        ConverterLayer(
            state = state,
            component = component
        )
        TopBarLayer(
            lastFetchTimeText = state.fetchDateTimeText
        )
        // Draw currency picker screen like a dialog: on top of the converter screen
        slot.child?.instance?.let { currencyListComponent ->
            Surface {
                CurrencyListContent(
                    component = currencyListComponent
                )
            }
        }
    }
}