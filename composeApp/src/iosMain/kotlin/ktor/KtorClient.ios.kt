package ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

actual fun ktorClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    TODO("Not yet implemented")
}