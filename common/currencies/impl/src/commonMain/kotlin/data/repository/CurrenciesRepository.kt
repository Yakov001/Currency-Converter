package data.repository

import data.Response
import data.data_source.ktor.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import data.model.CurrencyDto
import data.model.CurrencyInitial
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CurrenciesRepository(
    private val dataSource: KtorCurrenciesDataSource,
    private val kStore : KStoreDataSource
) {
    suspend fun getCurrencies(): Flow<Response<List<CurrencyDto>>> = channelFlow {
        // first, return what we have saved locally, then try to get updates
        val localData = kStore.getAllCurrencies()
        if (localData != null) {
            send(Response.Success(localData))
            return@channelFlow
        }
        // Request rates for USD, then request every currency (just for flag image)
        when (val initResponse = dataSource.getCurrenciesInitial()) {
            is Response.Loading -> send(Response.Loading())
            is Response.Failure -> send(Response.Failure(initResponse.message))
            is Response.Success -> {
                val mappedObject = initResponse.data.rates.ratesMap.map { CurrencyInitial(it.key, it.value) }
                val currencies = mutableListOf<CurrencyDto>()
                val mutex = Mutex()
                mappedObject.mapIndexed { _, obj ->
                    // for every currency in initial response, request detailed info (with flag)
                    launch {
                        val response = dataSource.getCurrenciesInitial(currencyCode = obj.currencyCode)
                        if (response is Response.Success) {
                            val data = response.data
                            mutex.withLock {
                                currencies.add(
                                    CurrencyDto(
                                        currencyCode = data.currencyCode,
                                        currencyName = data.currencyName,
                                        countryCode = data.countryCode,
                                        currencySymbol = data.currencySymbol,
                                        flagImageUrl = data.flagImage,
                                        usdRate = obj.usdRate
                                    )
                                )
                                // save to local storage
                                kStore.addCurrencies(currencies)
                                send(Response.Success(currencies.toList()))
                            }
                        }
                    }
                }.joinAll()
                if (currencies.isEmpty()) send(Response.Failure("List empty"))
            }
        }
    }.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
}