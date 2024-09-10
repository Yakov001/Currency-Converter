package presentation.decompose.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun SettingsContent(component: SettingsComponent) {
    val people by component.people.subscribeAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(
                items = people
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = "https://cdn1.ozone.ru/s3/multimedia-n/6805339511.jpg",
                        contentDescription = null,
                        imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier.fillMaxWidth(0.2f).aspectRatio(1f),
                        loading = { CircularProgressIndicator() }
                    )
                    Text(text = it.name)
                    Text(text = it.gender)
                    Text(text = it.height)
                }
            }
        }
    }
}