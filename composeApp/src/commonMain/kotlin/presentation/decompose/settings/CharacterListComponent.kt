package presentation.decompose.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import data.characters.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktor.KtorObject

interface CharacterListComponent {
    val characters : Value<List<Character>>
}

class CharacterListComponentImpl(
    componentContext: ComponentContext
) : CharacterListComponent, ComponentContext by componentContext {

    private val _characters: MutableValue<List<Character>> = MutableValue(emptyList())
    override val characters: Value<List<Character>> = _characters

    private fun fetchCharacters() {
        CoroutineScope(Dispatchers.Default).launch {
            val characters = KtorObject.getCharacters()
            _characters.update { characters }
        }
    }

    init {
        fetchCharacters()
    }

}