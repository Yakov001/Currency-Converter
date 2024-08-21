package data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val title : String,
    val description : String,
    private val iconIndex : Int
) {
    val icon : ImageVector
        get() = Events.eventIcons[iconIndex % Events.startEventsSize]
    companion object {
        fun newRandom(index : Int) : Event = Event(
            title = "Ивент №$index",
            description = "Это очень важный ивент. На него точно нужно пойти.",
            iconIndex = index
        )
    }
}

object Events {
    const val startEventsSize = 3
    val startEvents : List<Event> = List(startEventsSize) {
        Event.newRandom(it)
    }
    val eventIcons = List(startEventsSize) {
        listOf(
            Icons.Outlined.FavoriteBorder,
            Icons.Outlined.Add,
            Icons.Outlined.List,
            Icons.Outlined.AccountBox,
            Icons.Outlined.Home,
        ).random()
    }
}