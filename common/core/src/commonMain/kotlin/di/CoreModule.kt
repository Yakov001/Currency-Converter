package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ktor.HttpEngineFactory
import org.koin.dsl.module

private const val BASE_URL = "https://pro-api.coinmarketcap.com"
private const val API_KEY = "c7849b7a-8562-415f-8dbe-551b4901bf18"

fun coreModule() = module {
    single<HttpClient> {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url(BASE_URL)
                header("X-CMC_PRO_API_KEY", API_KEY)
            }
        }
    }
}