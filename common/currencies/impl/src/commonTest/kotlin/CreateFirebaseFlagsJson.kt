import data.Response
import data.data_source.remote.KtorCurrenciesDataSource
import data.data_source.remote.dto.old_api.InitRequest
import data.model.CurrencyDto
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

    private val dataSource: KtorCurrenciesDataSource by lazy { get() }

    @Test
    fun makeKtorRequest() = runTest {
        startKoin {
            modules(
                coreModule(),
                currenciesModule()
            )
        }
        val currenciesDomain = mutableListOf<CurrencyDto>()

        request(dataSource).rates.ratesMap.entries.map {
            launch {
                val response = dataSource.getCurrenciesInitial(it.key)
                if (response is Response.Success) {
                    currenciesDomain.add(CurrencyDto(
                        currencyCode = response.data.currencyCode,
                        currencyName = response.data.currencyName,
                        flagImageUrl = response.data.flagImage,
                        usdRate = response.data.rates.ratesMap["usd"]!!
                    ))
                }
            }
        }.joinAll()

        val json = Json {
            ignoreUnknownKeys = true
            allowStructuredMapKeys = true
        }
        val jsonString = json.encodeToJsonElement(currenciesDomain).toString()
        repeat(100) { println("brake-line") }
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