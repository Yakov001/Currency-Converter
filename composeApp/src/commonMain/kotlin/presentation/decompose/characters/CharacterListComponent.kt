package presentation.decompose.characters

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import data.RemoteRepository
import data.characters.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CharacterListComponent {
    val characters: Value<List<Character>>
    val searchText: StateFlow<String>
    fun onSearchTextChange(newText: String)

    fun navigateToCharacter(character: Character)
}

@OptIn(FlowPreview::class)
class CharacterListComponentImpl(
    componentContext: ComponentContext,
    private val navigateToCharacter : (Character) -> Unit
) : CharacterListComponent, ComponentContext by componentContext, KoinComponent {

    private val remoteRepository : RemoteRepository by inject()

    private val _characters: MutableValue<List<Character>> = MutableValue(emptyList())
    override val characters: Value<List<Character>> = _characters

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    override val searchText: StateFlow<String> = _searchText.asStateFlow()

    init {
        fetchCharacters()
        CoroutineScope(Dispatchers.Default).launch {
            _searchText
                .debounce(500)
                .collectLatest {
                    if (it.isBlank()) fetchCharacters()
                    else fetchCharacters(name = it)
                }
        }
    }

    private fun fetchCharacters(name: String? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            val characters = remoteRepository.getCharacters(name = name)
            _characters.update { characters }
        }
    }

    override fun onSearchTextChange(newText: String) {
        _searchText.update { newText.trim() }
    }

    override fun navigateToCharacter(character: Character) {
        navigateToCharacter.invoke(character)
    }

}