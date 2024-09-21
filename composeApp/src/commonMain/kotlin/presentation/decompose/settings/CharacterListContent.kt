package presentation.decompose.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import data.characters.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.CharacterListCard
import presentation.theme.ResonanseTheme

@Composable
fun CharacterListContent(component: CharacterListComponent) {
    val characters by component.characters.subscribeAsState()
    val searchText by component.searchText.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = component::onSearchTextChange,
            modifier = Modifier.height(50.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = characters
            ) {
                CharacterListCard(
                    character = it,
                    onClick = { component.onCharacterClick(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@[Composable Preview]
private fun CharacterListContentPreview() {
    ResonanseTheme {
        CharacterListContent(
            component = object : CharacterListComponent {
                override val characters: Value<List<Character>>
                    get() = MutableValue(emptyList())
                override val searchText: StateFlow<String>
                    get() = MutableStateFlow("").asStateFlow()

                override fun onSearchTextChange(newText: String) {}

                override fun onCharacterClick(character: Character) {}

            }
        )
    }
}