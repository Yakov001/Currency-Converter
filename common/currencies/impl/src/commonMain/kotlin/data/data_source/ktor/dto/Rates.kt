package data.data_source.ktor.dto

data class Rates(
    val date: String,
    val ratesMap: Map<String, Double>
)