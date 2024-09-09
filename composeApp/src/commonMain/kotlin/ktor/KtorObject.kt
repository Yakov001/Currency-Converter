package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

object KtorObject {

    private val client = HttpClient()

    suspend fun getPersonString() : String {
        return client.get(FULL_URL).body()
    }

    private const val BASE_URL = "https://swapi.dev/"
    private const val FULL_URL = "https://swapi.dev/api/people/1/?format=json"
}