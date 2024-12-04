package data.data_source.ktor.dto.old_api

data class Rates(
    val date: String,
    val ratesMap: Map<String, Double>
)