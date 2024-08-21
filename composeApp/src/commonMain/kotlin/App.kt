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
import presentation.components.AddEventFab
import presentation.components.MyNavBar
import presentation.decompose.RootComponent
import presentation.decompose.component_event.EventContent
import presentation.decompose.component_list.ListContent
import presentation.theme.ResonanseTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    rootComponent: RootComponent
) = ResonanseTheme {

    val (onFabClick, setOnFabClick) = remember { mutableStateOf({}) }

    Scaffold(
        floatingActionButton = {
            AddEventFab(onFabClick = onFabClick)
        },
        bottomBar = {
            MyNavBar(
                selectedItemIndex = 1,
                onItemSelected = {

                }
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

                is RootComponent.Child.SettingsChild -> {

                }
            }
        }
    }
}