package data.data_source.remote

import data.Response
import data.data_source.remote.dto.new_api.NewApiRequest
import data.data_source.remote.dto.old_api.InitRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class KtorCurrenciesDataSource(
    private val httpClient: HttpClient,
    private val httpClientNew: HttpClient
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

    suspend fun getCurrenciesNew() : Response<NewApiRequest> {
        try {
            val response = httpClientNew.get {
                url {
                    path("/latest/USD")
                }
            }
            if (response.status.value in 200..299) {
                val body: NewApiRequest = response.body()
                return Response.Success(body)
            } else {
                return Response.Failure("error code: ${response.status.description}")
            }
        } catch (e: Exception) {
            return Response.Failure(e.message.toString())
        }
    }

}