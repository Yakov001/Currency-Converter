package presentation.decompose.list

import com.arkivanov.decompose.ComponentContext
import data.events.Event

interface ListComponent {
    fun navigateToEvent(event : Event)
}

class ListComponentImpl(
    componentContext: ComponentContext,
    private val navigateToEvent : (Event) -> Unit
) : ListComponent, ComponentContext by componentContext {
    override fun navigateToEvent(event: Event) {
        navigateToEvent.invoke(event)
    }
}