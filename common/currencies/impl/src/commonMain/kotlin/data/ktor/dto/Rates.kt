package data.ktor.dto

data class Rates(
    val date: String,
    val ratesMap: Map<String, Double>
)