package presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import currency_converter.common.converter.impl.generated.resources.Res
import currency_converter.common.converter.impl.generated.resources.button_switch_currencies_desc
import currency_converter.common.converter.impl.generated.resources.swap_vert
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ButtonSwitchCurrencies(
    onClick: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()
    val shape = CircleShape
    val duration = 500
    var enabled by remember { mutableStateOf(true) }
    IconButton(
        onClick = {
            scope.launch {
                enabled = false
                onClick()
                rotation.animateTo(
                    targetValue = rotation.value + 360,
                    animationSpec = tween(durationMillis = duration)
                )
                enabled = true
            }
        },
        enabled = enabled,
        modifier = Modifier
            .size(60.dp)
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = shape
            )
            .padding(8.dp)
            .rotate(degrees = rotation.value)
    ) {
        Icon(
            bitmap = imageResource(Res.drawable.swap_vert),
            contentDescription = stringResource(Res.string.button_switch_currencies_desc),
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}