package data.ktor

import data.ktor.dto.FirstRequest
import data.ktor.model.Currency
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorCoinDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getCurrencies(): List<Currency> {
        val response = httpClient.get {
            url {
                path("/usd")
            }
        }
        if (response.status.value in 200..299) {
            val body: FirstRequest = response.body()
            return body.rates.usdRates.map { Currency(it.key, it.value) }
        } else return emptyList()
    }

}