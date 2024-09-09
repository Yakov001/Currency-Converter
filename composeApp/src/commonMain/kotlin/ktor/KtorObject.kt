package ktor

import data.people.Person
import data.people.PersonWrapper
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

    private const val BASE_URL = "https://swapi.dev/api/"
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

    suspend fun getPersonString() : String {
        return client.get("people/1/?format=json").body()
    }

    suspend fun getPeople() : List<Person> {
        val wrapper : PersonWrapper = client.get {
            url {
                path("people/")
                parameter("format", "json")
            }
        }.body()
        return wrapper.person
    }

    suspend fun getPeopleJson() : String {
        return client.get{url{path("people/?format=json")}}.body()
    }
}