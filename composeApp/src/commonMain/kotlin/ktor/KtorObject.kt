package ktor

import data.characters.Character
import data.characters.CharacterWrapper
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorObject {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val client = ktorClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        defaultRequest {
            url(BASE_URL)
        }
    }

    suspend fun getCharacters(page : Int = 0) : List<Character> {
        val characters : CharacterWrapper = client.get {
            url {
                path("character/")
                parameter("page", page)
            }
        }.body()
        return characters.results
    }

}