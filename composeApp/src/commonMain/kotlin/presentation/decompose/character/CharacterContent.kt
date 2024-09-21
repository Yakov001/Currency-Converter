package presentation.decompose.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.crossfade

@Composable
fun CharacterContent(component: CharacterComponent) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize().padding(vertical = 32.dp, horizontal = 16.dp)
    ) {
        SubcomposeAsyncImage(
            model = component.character.imageUrl,
            contentDescription = null,
            imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxWidth(0.6f).aspectRatio(1f),
            loading = { CircularProgressIndicator() }
        )
        Text(
            text = component.character.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = component.character.species
        )
        Text(
            text = component.character.gender
        )
    }
}