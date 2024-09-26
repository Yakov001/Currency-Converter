package data.ktor.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("sign")
    val sign: String,
    @SerialName("symbol")
    val symbol: String
)