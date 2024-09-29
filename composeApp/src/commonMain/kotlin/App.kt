import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import presentation.composables.MyNavBar
import presentation.decompose.CurrencyListContent
import presentation.decompose.RootComponent
import presentation.decompose.character.CharacterContent
import presentation.decompose.characters.CharacterListContent
import theme.ResonanseTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    rootComponent: RootComponent
) = ResonanseTheme {

    val currentScreen = rootComponent.stack.subscribeAsState().value.active.instance

    Scaffold(
        bottomBar = {
            MyNavBar(
                currentScreen = currentScreen,
                toCurrencyList = rootComponent::navigateToCurrencyList,
                toSettings = rootComponent::navigateToCharacterList
            )
        }
    ) { paddingValues ->

        Children(
            modifier = Modifier.padding(paddingValues),
            stack = rootComponent.stack,
            animation = predictiveBackAnimation(
                backHandler = rootComponent.backHandler,
                fallbackAnimation = stackAnimation (fade() + scale()),
                onBack = rootComponent::onBackClicked
            )
        ) {
            when (val child = it.instance) {

                is RootComponent.Child.CharacterListChild -> {
                    CharacterListContent(
                        component = child.component
                    )
                }

                is RootComponent.Child.CharacterChild -> {
                    CharacterContent(
                        component = child.component
                    )
                }

                is RootComponent.Child.CurrencyListChild -> {
                    CurrencyListContent(
                        component = child.component
                    )
                }
            }
        }
    }
}