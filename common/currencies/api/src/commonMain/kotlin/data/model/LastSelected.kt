package data.model

import kotlinx.serialization.Serializable

@Serializable
data class LastSelected(
    val from: CurrencyDto,
    val to: CurrencyDto
)
