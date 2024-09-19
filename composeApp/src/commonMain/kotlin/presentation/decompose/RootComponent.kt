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
import data.events.Event
import kotlinx.serialization.Serializable
import presentation.decompose.RootComponent.Child.EventChild
import presentation.decompose.RootComponent.Child.ListChild
import presentation.decompose.RootComponent.Child.CharacterListChild
import presentation.decompose.event.EventComponent
import presentation.decompose.event.EventComponentImpl
import presentation.decompose.list.ListComponent
import presentation.decompose.list.ListComponentImpl
import presentation.decompose.settings.CharacterListComponent
import presentation.decompose.settings.CharacterListComponentImpl

interface RootComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    fun navigateToList()

    fun navigateToCharacterList()

    // Defines all possible child components
    sealed class Child {
        class ListChild(val component: ListComponent) : Child()
        class CharacterListChild(val component: CharacterListComponent) : Child()
        class EventChild(val component: EventComponent) : Child()
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
            initialConfiguration = Config.List, // The initial child component is List
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

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.List -> ListChild(listComponent(componentContext))
            is Config.CharacterList -> CharacterListChild(detailsComponent(componentContext))
            is Config.Event -> EventChild(eventComponent(
                componentContext = componentContext,
                event = config.event
            ))
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

    private fun detailsComponent(componentContext: ComponentContext): CharacterListComponent =
        CharacterListComponentImpl(
            componentContext = componentContext
        )

    private fun eventComponent(componentContext: ComponentContext, event: Event): EventComponent =
        EventComponentImpl(
            componentContext = componentContext,
            event = event
        )

    @Serializable
    private sealed interface Config {

        @Serializable
        data object List : Config

        @Serializable
        data object CharacterList : Config

        @Serializable
        data class Event(val event: data.events.Event) : Config
    }

}