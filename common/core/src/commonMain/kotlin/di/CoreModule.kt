package di

import com.resonanse.common.core.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ktor.HttpEngineFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module


private const val BASE_URL_NEW = "https://v6.exchangerate-api.com/v6/"
private const val BASE_URL_OLD = "https://currency-rate-exchange-api.onrender.com/"

const val NEW_API_KOIN_QUALIFIER = "new_api"
const val OLD_API_KOIN_QUALIFIER = "old_api"

fun coreModule() = module {
    single<HttpClient>(qualifier = named(NEW_API_KOIN_QUALIFIER)) {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    host = BASE_URL_NEW
                    parameters.append("api-key", BuildKonfig.API_KEY)
                }
            }
        }
    }
    single<HttpClient>(qualifier = named(OLD_API_KOIN_QUALIFIER)) {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url(BASE_URL_OLD)
            }
        }
    }
}