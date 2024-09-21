package data.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("results")
data class Character(
    val name: String,
    val gender: String,
    @SerialName("image") val imageUrl: String,
    val species: String,
    val origin: Origin,
    val location: Location
) {
    @Serializable
    data class Origin(
        val name: String
    )

    @Serializable
    data class Location(
        val name: String
    )
}
