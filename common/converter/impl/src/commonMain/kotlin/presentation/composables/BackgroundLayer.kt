package presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import resonanse.common.converter.impl.generated.resources.Res
import resonanse.common.converter.impl.generated.resources.background

@Composable
fun BackgroundLayer() {

    var offset by remember { mutableStateOf(Offset.Zero) }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        val shape = MaterialTheme.shapes.extraLarge.copy(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f, true)
                .fillMaxWidth()
                .animateContentSize()
                .onGloballyPositioned { coordinates ->
                    offset = Offset(
                        x = coordinates.size.width.toFloat(),
                        y = coordinates.size.height.toFloat()
                    )
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                            MaterialTheme.colorScheme.primary
                        ),
                        start = Offset.Zero,
                        end = offset
                    ),
                    shape = shape
                )
                .paint(
                    painter = painterResource(Res.drawable.background),
                    contentScale = ContentScale.Crop,
                )
        )
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