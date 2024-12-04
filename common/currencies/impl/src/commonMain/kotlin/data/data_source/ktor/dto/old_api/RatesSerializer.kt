@file:OptIn(ExperimentalSerializationApi::class)

package data.data_source.ktor.dto.old_api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object RatesSerializer : KSerializer<Rates> {

    override val descriptor: SerialDescriptor by lazy {
        buildClassSerialDescriptor("data.ktor.dto.Rates") {
            element<String>("date")
            element<Map<String, Double>>("currencyRates")
        }
    }

    override fun serialize(encoder: Encoder, value: Rates) {
        val jsonEncoder = encoder as JsonEncoder
        val jsonObject = buildJsonObject {
            put("date", JsonPrimitive(value.date))
        }
        jsonEncoder.encodeJsonElement(jsonObject)
    }

    override fun deserialize(decoder: Decoder): Rates {
        val jsonDecoder = decoder as JsonDecoder
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val date = jsonObject["date"]?.jsonPrimitive?.content!!

        val currencyRates: Map<String, Double> = jsonObject
            .filterKeys { it != "date" }
            .values
            .first()
            .jsonObject
            .mapValues {
                it.value.jsonPrimitive.content.toDouble()
            }

        return Rates(date = date, ratesMap = currencyRates)
    }
}