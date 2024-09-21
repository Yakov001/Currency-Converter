package presentation.decompose.character

import com.arkivanov.decompose.ComponentContext
import data.characters.Character

interface CharacterComponent {
    val character: Character
}

class CharacterComponentImpl(
    componentContext: ComponentContext,
    override val character: Character
) : CharacterComponent, ComponentContext by componentContext {

}