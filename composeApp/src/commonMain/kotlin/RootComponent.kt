import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable
import RootComponent.Child.CurrencyListChild
import presentation.decompose.CurrencyListComponent
import presentation.decompose.CurrencyListComponentImpl

interface RootComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()

    fun navigateToCurrencyList()

    sealed class Child {
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
            initialConfiguration = Config.CurrencyList,
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigateToCurrencyList() {
        navigation.replaceAll(Config.CurrencyList)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.CurrencyList -> CurrencyListChild(currencyListComponent(componentContext))
        }

    private fun currencyListComponent(componentContext: ComponentContext) : CurrencyListComponent =
        CurrencyListComponentImpl(
            componentContext = componentContext
        )

    @Serializable
    private sealed interface Config {

        @Serializable
        data object CurrencyList : Config

    }

}