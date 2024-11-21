import RootComponent.Child.ConverterChild
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable
import presentation.decompose.ConverterComponent
import presentation.decompose.ConverterComponentImpl

interface RootComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    sealed class Child {
        class ConverterChild(val component: ConverterComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = RootConfig.Converter,
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child
        )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun child(config: RootConfig, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is RootConfig.Converter -> ConverterChild(converterComponent(componentContext))
        }

    private fun converterComponent(componentContext: ComponentContext): ConverterComponent =
        ConverterComponentImpl(
            componentContext = componentContext
        )

    @Serializable
    private sealed interface RootConfig {
        @Serializable
        data object Converter : RootConfig
    }

}