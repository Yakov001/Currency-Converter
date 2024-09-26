package data.ktor.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("credit_count")
    val creditCount: Int,
    @SerialName("elapsed")
    val elapsed: Int,
    @SerialName("error_code")
    val errorCode: Int? = null,
    @SerialName("error_message")
    val errorMessage: String? = null,
    @SerialName("timestamp")
    val timestamp: String
)