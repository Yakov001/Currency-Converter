import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.statekeeper.SerializableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import di.resonanseKoinAppDeclaration
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.koin.compose.KoinApplication
import org.w3c.dom.get
import org.w3c.dom.set

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher(
        savedState = localStorage[KEY_SAVED_STATE]?.decodeSerializableContainer()
    )

    lifecycle.resume()

    window.onbeforeunload = {
        localStorage[KEY_SAVED_STATE] = stateKeeper.save().encodeToString()
        null
    }

    val componentContext = DefaultComponentContext(
        lifecycle = lifecycle,
        stateKeeper = stateKeeper
    )

    val rootComponent = RootComponentImpl(componentContext = componentContext)

    ComposeViewport(document.body!!) {
        KoinApplication(application = resonanseKoinAppDeclaration()) {
            App(
                rootComponent = rootComponent
            )
        }
    }

}

private const val KEY_SAVED_STATE = "saved_state"

private val json = Json { allowStructuredMapKeys = true }

private fun SerializableContainer.encodeToString(): String =
    json.encodeToString(SerializableContainer.serializer(), this)

private fun String.decodeSerializableContainer(): SerializableContainer? =
    try {
        json.decodeFromString(SerializableContainer.serializer(), this)
    } catch (e: Exception) {
        null
    }