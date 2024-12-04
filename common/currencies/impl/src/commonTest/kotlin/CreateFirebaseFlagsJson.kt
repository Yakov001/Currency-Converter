import data.Response
import data.data_source.ktor.KtorCurrenciesDataSource
import data.data_source.ktor.dto.InitRequest
import di.coreModule
import di.currenciesModule
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test

class CreateFirebaseFlagsJson : KoinTest {
    @Test
    fun makeKtorRequest() = runTest {
        startKoin {
            modules(
                coreModule(),
                currenciesModule()
            )
        }
        val dataSource: KtorCurrenciesDataSource = get()

        val initRequest = request(dataSource)

        val validCurrencyNameAndFlagUrl = mutableMapOf<String, String>()
        val validCurrencies = initRequest.rates.ratesMap.entries.map {
            launch {
                val response = dataSource.getCurrenciesInitial(it.key)
                if (response is Response.Success) {
                    validCurrencyNameAndFlagUrl[it.key] = response.data.flagImage
                }
            }
        }.joinAll()

        println(validCurrencyNameAndFlagUrl.entries.joinToString { it.key + ": " + it.value + "\n" })

        val json = Json {
            ignoreUnknownKeys = true
            allowStructuredMapKeys = true
        }
        val jsonString = json.encodeToJsonElement(validCurrencyNameAndFlagUrl).toString()
        println(jsonString)
    }

    private suspend fun request(dataSource: KtorCurrenciesDataSource) : InitRequest {
        return try {
            when(val data = dataSource.getCurrenciesInitial()) {
                is Response.Success -> data.data
                else -> return request(dataSource)
            }
        } catch (e: SocketTimeoutException) {
            delay(5000)
            return request(dataSource)
        }
    }

}