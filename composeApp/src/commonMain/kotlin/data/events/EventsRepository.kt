package data.events

import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object EventsRepository {

    private val _events = MutableStateFlow(Events.startEvents.toMutableStateList())
    val events : StateFlow<List<Event>> = _events.asStateFlow()

    fun addEvent() {
        _events.value.add(
            index = 0,
            element = Event.newRandom(_events.value.size)
        )
    }
}