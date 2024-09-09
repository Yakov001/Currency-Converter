package ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun ktorClient(block: HttpClientConfig<*>.() -> Unit = {}) : HttpClient