package data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

actual fun ktorClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(OkHttp, block)
}