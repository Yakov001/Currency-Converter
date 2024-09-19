package data.characters


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterWrapper(
    @SerialName("results")
    val results: List<Character>
)