package data.ktor.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FiatWrapper(
    @SerialName("data")
    val data: List<Currency>,
    @SerialName("status")
    val status: Status
)