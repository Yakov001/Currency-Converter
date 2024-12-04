package presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.crossfade
import domain.model.CurrencyEntity

@Composable
fun CurrencyListCard(currency: CurrencyEntity, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f, fill = true)
            ) {
                FlagImage(currency.flagImageUrl)
                Text(
                    text = currency.currencyName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = currency.currencyCode,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun FlagImage(model: String) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = null,
        imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
            .crossfade(true)
            .build(),
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ),
        loading = { CircularProgressIndicator() },
        contentScale = ContentScale.Crop
    )
}