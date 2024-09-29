package data.ktor.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rates(
    @SerialName("date")
    val date: String,
    @SerialName("usd")
    val usdRates: Map<String, Double>
)