import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import presentation.components.AddEventFab
import presentation.components.MyNavBar
import presentation.decompose.RootComponent
import presentation.decompose.character.CharacterContent
import presentation.decompose.event.EventContent
import presentation.decompose.list.ListContent
import presentation.decompose.characters.CharacterListContent
import theme.ResonanseTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    rootComponent: RootComponent
) = ResonanseTheme {

    val (onFabClick, setOnFabClick) = remember { mutableStateOf({}) }
    val currentScreen = rootComponent.stack.subscribeAsState().value.active.instance

    Scaffold(
        floatingActionButton = {
            if (currentScreen is RootComponent.Child.ListChild) {
                AddEventFab(onFabClick = onFabClick)
            }
        },
        bottomBar = {
            MyNavBar(
                currentScreen = currentScreen,
                toList = rootComponent::navigateToList,
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
                is RootComponent.Child.ListChild -> {
                    ListContent(
                        component = child.component,
                        setOnFabClick = setOnFabClick
                    )
                }

                is RootComponent.Child.EventChild -> {
                    EventContent(
                        component = child.component
                    )
                }

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
            }
        }
    }
}