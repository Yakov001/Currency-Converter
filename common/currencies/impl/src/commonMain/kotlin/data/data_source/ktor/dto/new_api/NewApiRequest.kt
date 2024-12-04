package data.data_source.ktor.dto.new_api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewApiRequest(
    @SerialName("base_code")
    val baseCode: String,
    @SerialName("conversion_rates")
    val conversionRates: ConversionRates,
    @SerialName("documentation")
    val documentation: String,
    @SerialName("result")
    val result: String,
    @SerialName("terms_of_use")
    val termsOfUse: String,
    @SerialName("time_last_update_unix")
    val timeLastUpdateUnix: Int,
    @SerialName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerialName("time_next_update_unix")
    val timeNextUpdateUnix: Int,
    @SerialName("time_next_update_utc")
    val timeNextUpdateUtc: String
)