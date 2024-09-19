package data.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("results")
data class Character(
    val name: String,
    val gender: String,
    @SerialName("image")
    val imageUrl: String
)
