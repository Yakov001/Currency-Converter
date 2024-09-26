package data.ktor

import data.ktor.dto.Currency
import data.ktor.dto.FiatWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path

class KtorCoinDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getCurrencies() : List<Currency> {
        val response = httpClient.get {
            url {
                path("/v1/fiat/map")
            }
        }
        if (response.status.value in 200..299) {
            val body : FiatWrapper = response.body()
            return body.data
        } else return emptyList()
    }

}