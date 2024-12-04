@file:OptIn(ExperimentalSerializationApi::class)

package data.data_source.ktor.dto.new_api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object RatesNewSerializer : KSerializer<RatesNew> {

    override val descriptor: SerialDescriptor by lazy {
        buildClassSerialDescriptor("data.ktor.dto.newApi.RatesNew") {
            element<Map<String, Double>>("currencyRates")
        }
    }

    override fun serialize(encoder: Encoder, value: RatesNew) {
        val jsonEncoder = encoder as JsonEncoder
        val jsonObject = buildJsonObject {
//            put("date", JsonPrimitive(value.date))
        }
        jsonEncoder.encodeJsonElement(jsonObject)
    }

    override fun deserialize(decoder: Decoder): RatesNew {
        val jsonDecoder = decoder as JsonDecoder
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val currencyRates: Map<String, Double> = jsonObject
            .filterKeys { it != "date" }
            .values
            .first()
            .jsonObject
            .mapValues {
                it.value.jsonPrimitive.content.toDouble()
            }

        return RatesNew(ratesMap = currencyRates)
    }
}