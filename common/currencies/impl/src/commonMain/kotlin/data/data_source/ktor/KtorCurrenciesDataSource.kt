package data.data_source.ktor

import data.Response
import data.data_source.ktor.dto.InitRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorCurrenciesDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getCurrenciesInitial(currencyCode: String = "usd"): Response<InitRequest> {
        try {
            val response = httpClient.get {
                url {
                    path("/${currencyCode}")
                }
            }
            if (response.status.value in 200..299) {
                val body: InitRequest = response.body()
                return Response.Success(body)
            } else {
                return Response.Failure("error code: ${response.status.description}")
            }
        } catch (e: Exception) {
            return Response.Failure(e.message?.take(20).toString())
        }
    }

}