package data.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonWrapper(
    @SerialName("results")
    val person: List<Person>
)

@Serializable
data class Person(
    val name: String,
    val height: String,
    val gender: String
)
