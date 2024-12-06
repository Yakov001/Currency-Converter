package presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import currency_converter.common.currencies.impl.generated.resources.Res
import currency_converter.common.currencies.impl.generated.resources.arrow_upward
import currency_converter.common.currencies.impl.generated.resources.currency_code
import currency_converter.common.currencies.impl.generated.resources.currency_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.model.SortOption

@Composable
fun SortingOptionsRow(
    sortOption: SortOption,
    onSortOrderChange: (SortOption) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        SortOptionTappableBox(
            text = stringResource(Res.string.currency_name),
            onClick = { onSortOrderChange(SortOption.CurrencyName(ascending = !sortOption.ascending)) },
            isAscending = (sortOption as? SortOption.CurrencyName)?.ascending
        )
        SortOptionTappableBox(
            text = stringResource(Res.string.currency_code),
            onClick = { onSortOrderChange(SortOption.CurrencyCode(ascending = !sortOption.ascending)) },
            isAscending = (sortOption as? SortOption.CurrencyCode)?.ascending
        )
    }
}

@Composable
private fun SortOptionTappableBox(
    text: String,
    onClick: () -> Unit,
    // if null - no icon, if true - ascending, if false - descending
    isAscending: Boolean? = null
) {
    val isIconVisible = isAscending != null

    val rotationDegrees by animateFloatAsState(
        targetValue = if (isAscending == true) 0f else 180f,
        animationSpec = tween(durationMillis = 500)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onClick()
            }
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
        AnimatedVisibility(visible = isIconVisible) {
            Spacer(modifier = Modifier.width(8.dp))
        }
        AnimatedVisibility(visible = isIconVisible) {
            Icon(
                painter = painterResource(Res.drawable.arrow_upward),
                contentDescription = null,
                modifier = Modifier.rotate(degrees = rotationDegrees)
            )
        }
    }
}