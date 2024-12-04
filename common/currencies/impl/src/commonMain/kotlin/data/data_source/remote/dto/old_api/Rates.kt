package data.data_source.remote.dto.old_api

data class Rates(
    val date: String,
    val ratesMap: Map<String, Double>
)