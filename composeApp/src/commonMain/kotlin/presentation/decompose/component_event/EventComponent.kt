package presentation.decompose.component_event

import com.arkivanov.decompose.ComponentContext
import data.Event

interface EventComponent {
    val event : Event
}

class EventComponentImpl(
    componentContext: ComponentContext,
    override val event: Event
) : EventComponent