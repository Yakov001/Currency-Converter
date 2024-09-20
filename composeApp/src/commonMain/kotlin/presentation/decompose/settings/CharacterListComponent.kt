package presentation.decompose.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import data.characters.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ktor.KtorObject
import utils.Log

interface CharacterListComponent {
    val characters: Value<List<Character>>
    val searchText: StateFlow<String>
    fun onSearchTextChange(newText: String)
    fun onCharacterClick(character: Character)
}

@OptIn(FlowPreview::class)
class CharacterListComponentImpl(
    componentContext: ComponentContext
) : CharacterListComponent, ComponentContext by componentContext {

    private val _characters: MutableValue<List<Character>> = MutableValue(emptyList())
    override val characters: Value<List<Character>> = _characters

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    override val searchText: StateFlow<String> = _searchText.asStateFlow()

    init {
        fetchCharacters()
        CoroutineScope(Dispatchers.Default).launch {
            _searchText
                .debounce(500)
                .filterNot { it.isBlank() }
                .collectLatest {
                    Log.d("search: $it")
                    searchCharacter(it)
                }
        }
    }

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.Default).launch {
            val characters = KtorObject.getCharacters()
            _characters.update { characters }
        }
    }

    override fun onSearchTextChange(newText: String) {
        _searchText.update { newText }
    }

    override fun onCharacterClick(character: Character) {

    }

    private suspend fun searchCharacter(name: String) {
        val characters = KtorObject.getCharacters(name = name)
        _characters.update { characters }
    }

}