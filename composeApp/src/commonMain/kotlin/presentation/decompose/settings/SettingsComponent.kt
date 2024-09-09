package presentation.decompose.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import data.people.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktor.KtorObject
import utils.Log

interface SettingsComponent {
    val people : Value<List<Person>>
}

class SettingsComponentImpl(
    componentContext: ComponentContext
) : SettingsComponent, ComponentContext by componentContext {

    private val _people: MutableValue<List<Person>> = MutableValue(emptyList())
    override val people: Value<List<Person>> = _people

    private fun fetchPeople() {
        CoroutineScope(Dispatchers.Default).launch {
            val people = KtorObject.getPeople()
            _people.update { people }
        }
    }

    init {
        fetchPeople()
    }

}