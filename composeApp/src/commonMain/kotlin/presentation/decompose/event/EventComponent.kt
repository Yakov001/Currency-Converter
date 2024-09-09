package presentation.decompose.event

import com.arkivanov.decompose.ComponentContext
import data.events.Event

interface EventComponent {
    val event : Event
}

class EventComponentImpl(
    componentContext: ComponentContext,
    override val event: Event
) : EventComponent