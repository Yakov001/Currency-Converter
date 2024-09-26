package presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import data.characters.Character
import data.events.Event
import kotlinx.serialization.Serializable
import presentation.decompose.RootComponent.Child.*
import presentation.decompose.character.CharacterComponent
import presentation.decompose.character.CharacterComponentImpl
import presentation.decompose.event.EventComponent
import presentation.decompose.event.EventComponentImpl
import presentation.decompose.list.ListComponent
import presentation.decompose.list.ListComponentImpl
import presentation.decompose.characters.CharacterListComponent
import presentation.decompose.characters.CharacterListComponentImpl

interface RootComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    fun navigateToList()

    fun navigateToCharacterList()

    fun navigateToCurrencyList()

    // Defines all possible child components
    sealed class Child {
        class ListChild(val component: ListComponent) : Child()
        class CharacterListChild(val component: CharacterListComponent) : Child()
        class EventChild(val component: EventComponent) : Child()
        class CharacterChild(val component: CharacterComponent) : Child()
        class CurrencyListChild(val component: CurrencyListComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.CurrencyList, // The initial child component is List
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigateToList() {
        navigation.replaceAll(Config.List)
    }

    override fun navigateToCharacterList() {
        navigation.replaceAll(Config.CharacterList)
    }

    override fun navigateToCurrencyList() {
        navigation.replaceAll(Config.CurrencyList)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.List -> ListChild(listComponent(componentContext))
            is Config.CharacterList -> CharacterListChild(characterListComponent(componentContext))
            is Config.Event -> EventChild(eventComponent(
                componentContext = componentContext,
                event = config.event
            ))
            is Config.Character -> CharacterChild(characterComponent(
                componentContext = componentContext,
                character = config.character
            ))
            is Config.CurrencyList -> CurrencyListChild(currencyListComponent(componentContext))
        }

    private fun listComponent(componentContext: ComponentContext): ListComponent =
        ListComponentImpl(
            componentContext = componentContext,
            navigateToEvent = { event ->
                navigation.push(
                    Config.Event(
                        event = event
                    )
                )
            },
        )

    private fun characterListComponent(componentContext: ComponentContext): CharacterListComponent =
        CharacterListComponentImpl(
            componentContext = componentContext,
            navigateToCharacter = { character ->
                navigation.push(
                    Config.Character(
                        character = character
                    )
                )
            }
        )

    private fun eventComponent(componentContext: ComponentContext, event: Event): EventComponent =
        EventComponentImpl(
            componentContext = componentContext,
            event = event
        )

    private fun characterComponent(componentContext: ComponentContext, character: Character): CharacterComponent =
        CharacterComponentImpl(
            componentContext = componentContext,
            character = character
        )

    private fun currencyListComponent(componentContext: ComponentContext) : CurrencyListComponent =
        CurrencyListComponentImpl(
            componentContext = componentContext
        )

    @Serializable
    private sealed interface Config {

        @Serializable
        data object List : Config

        @Serializable
        data object CharacterList : Config

        @Serializable
        data object CurrencyList : Config

        @Serializable
        data class Event(val event: data.events.Event) : Config

        @Serializable
        data class Character(val character: data.characters.Character) : Config
    }

}