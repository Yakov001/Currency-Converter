package data.ktor.dto

import data.ktor.RatesSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitRequest(
    @SerialName("countryCode")
    val countryCode: String,
    @SerialName("countryName")
    val countryName: String,
    @SerialName("currencyCode")
    val currencyCode: String,
    @SerialName("currencyName")
    val currencyName: String,
    @SerialName("currencySymbol")
    val currencySymbol: String,
    @SerialName("flagImage")
    val flagImage: String,
    @SerialName("rates")
    @Serializable(with = RatesSerializer::class)
    val rates: Rates
)