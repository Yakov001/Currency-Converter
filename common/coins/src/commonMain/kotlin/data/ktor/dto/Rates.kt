package data.ktor.dto

data class Rates(
    val date: String,
    val rates: Map<String, Double>
)