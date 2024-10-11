import RootComponent.Child.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable
import presentation.decompose.ConverterComponent
import presentation.decompose.ConverterComponentImpl
import presentation.decompose.CurrencyListComponent
import presentation.decompose.CurrencyListComponentImpl

interface RootComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    sealed class Child {
        class CurrencyListChild(val component: CurrencyListComponent) : Child()
        class ConverterChild(val component: ConverterComponent) : Child()
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
            initialConfiguration = Config.Converter,
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child
        )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.CurrencyList -> CurrencyListChild(currencyListComponent(componentContext))
            is Config.Converter -> ConverterChild(converterComponent(componentContext))
        }

    private fun currencyListComponent(componentContext: ComponentContext) : CurrencyListComponent =
        CurrencyListComponentImpl(
            componentContext = componentContext
        )

    private fun converterComponent(componentContext: ComponentContext) : ConverterComponent =
        ConverterComponentImpl(
            componentContext = componentContext,
            toCurrencyList = {
                navigation.push(Config.CurrencyList)
            }
        )

    @Serializable
    private sealed interface Config {

        @Serializable
        data object CurrencyList : Config

        @Serializable
        data object Converter : Config

    }

}