package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import data.characters.Character

@[Composable]
fun CharacterListCard(
    character: Character,
    onClick : () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            SubcomposeAsyncImage(
                model = character.imageUrl,
                contentDescription = null,
                imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxHeight().aspectRatio(1f),
                loading = { CircularProgressIndicator() }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = character.gender
                )
            }
        }
    }
}