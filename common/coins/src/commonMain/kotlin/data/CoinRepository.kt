package data

import data.ktor.KtorCoinDataSource
import data.model.Currency
import data.model.CurrencyInitial
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import utils.Log

class CoinRepository(
    private val dataSource: KtorCoinDataSource
) {
    suspend fun getCurrencies(): Response<List<Currency>> = coroutineScope {
        when (val initResponse = dataSource.getCurrenciesInitial()) {
            is Response.Failure -> return@coroutineScope Response.Failure(initResponse.message)
            is Response.Success -> {
                val mappedObject = initResponse.data.rates.usdRates.map { CurrencyInitial(it.key, it.value) }

                val currencies : ArrayList<Currency> = arrayListOf()
                mappedObject.mapIndexed { i, obj ->
                    async {
                        Log.d(text = "$i start")
                        val response = dataSource.getCurrenciesInitial(currencyCode = obj.name)
                        if (response is Response.Success) {
                            val data = response.data
                            currencies.add(
                                Currency(
                                    currencyCode = data.currencyCode,
                                    countryCode = data.countryCode,
                                    currencySymbol = data.currencySymbol,
                                    flagImageUrl = data.flagImage,
                                    usdRate = initResponse.data.rates.usdRates.getOrElse(data.currencyCode, {0.0})
                                )
                            )
                        }
                        Log.d(text = "$i end")
                    }
                }.awaitAll()
                return@coroutineScope if (!currencies.isEmpty()) Response.Success(currencies) else Response.Failure()
            }
        }
    }
}