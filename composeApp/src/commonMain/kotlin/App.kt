import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.launch
import presentation.composables.MyNavBar
import presentation.decompose.CurrencyListContent
import presentation.decompose.RootComponent
import presentation.decompose.character.CharacterContent
import presentation.decompose.characters.CharacterListContent
import theme.ResonanseTheme
import utils.ObserveAsEvents
import utils.SnackbarController

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    rootComponent: RootComponent
) = ResonanseTheme {

    val currentScreen = rootComponent.stack.subscribeAsState().value.active.instance

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = SnackbarController.events,
        key1 = snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
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