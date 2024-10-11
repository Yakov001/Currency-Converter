import data.ktor.dto.InitRequest
import data.ktor.dto.Rates
import kotlinx.serialization.json.Json
import kotlin.test.Test

class RatesSerializerTest2 {

    private val json = Json {
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
    }

    @Test
    fun when_request_rub_serializer_works() {
        val jsonStringRub : String = responseForRub
        val jsonStringUsd : String = responseForUsd
        val jsonStringStub : String = encodeStubToJson()

        val rates : InitRequest = json.decodeFromString(
            deserializer = InitRequest.serializer(),
            string = jsonStringUsd
        )
        println("rates = $rates")
        assert(rates.rates.date.isNotEmpty())
        assert(rates.rates.ratesMap.isNotEmpty())
    }

    private fun encodeStubToJson() : String = json.encodeToJsonElement(
        value = stubInitRequest,
        serializer = InitRequest.serializer()
    ).toString()

    companion object {
        val stubInitRequest = InitRequest(
            countryCode = "codde",
            countryName = "cName",
            currencyCode = "CODE",
            currencySymbol = "amog",
            flagImage = "sdlfjsd",
            currencyName = "name",
            rates = Rates(
                date = "24072001",
                ratesMap = mapOf(
                    "usd" to 1.0,
                    "rub" to 2.0,
                    "zwl" to 35207.56422877
                )
            )
        )
    }

}