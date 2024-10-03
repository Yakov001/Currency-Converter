package data

import data.ktor.KtorCoinDataSource
import data.model.Currency
import data.model.CurrencyInitial
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import utils.Log

class CoinRepository(
    private val dataSource: KtorCoinDataSource
) {
    suspend fun getCurrencies(): Flow<Response<List<Currency>>> = channelFlow {
        when (val initResponse = dataSource.getCurrenciesInitial()) {
            is Response.Loading -> send(Response.Loading())
            is Response.Failure -> send(Response.Failure(initResponse.message))
            is Response.Success -> {
                val mappedObject = initResponse.data.rates.rates.map { CurrencyInitial(it.key, it.value) }
                Log.d("mappedObjectCount = ${mappedObject.count()}")
                val currencies : ArrayList<Currency> = arrayListOf()
                mappedObject.mapIndexed { i, obj ->
                    launch {
                        Log.d(text = "$i start")
                        val response = dataSource.getCurrenciesInitial(currencyCode = obj.currencyCode)
                        if (response is Response.Success) {
                            val data = response.data
                            currencies.add(
                                Currency(
                                    currencyCode = data.currencyCode,
                                    countryCode = data.countryCode,
                                    currencySymbol = data.currencySymbol,
                                    flagImageUrl = data.flagImage,
                                    usdRate = obj.usdRate
                                )
                            )
                            send(Response.Success(currencies))
                        }
                        Log.d(text = "$i end")
                    }
                }.joinAll()
                if (currencies.isEmpty()) send(Response.Failure("List empty"))
            }
        }
    }
}