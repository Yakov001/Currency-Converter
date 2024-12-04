package data.repository

import data.Response
import data.data_source.remote.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import data.model.CurrencyInitial
import domain.model.CurrencyEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CurrenciesRepository(
    private val dataSource: KtorCurrenciesDataSource,
    private val kStore: KStoreDataSource
) {
    suspend fun getCurrencies(): Flow<Response<List<CurrencyEntity>>> = channelFlow {
        // first, return what we have saved locally, then try to get updates
        kStore.getAllCurrencies()?.also {
            send(Response.Success(it))
        } ?: launch {
            updateCurrenciesFromRemote().collectLatest { send(it) }
        }
    }.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun updateCurrenciesFromRemote() : Flow<Response<List<CurrencyEntity>>> = channelFlow {
        // Try to get updates from remote
        // Request rates for USD, then request every currency (just for flag image)
        when (val initResponse = dataSource.getCurrenciesInitial()) {
            is Response.Loading -> send(Response.Loading())
            is Response.Failure -> send(Response.Failure(initResponse.message))
            is Response.Success -> {
                val mappedObject = initResponse.data.rates.ratesMap.map { CurrencyInitial(it.key, it.value) }
                val result: MutableList<CurrencyEntity> = mutableListOf()
                val mutex = Mutex()
                mappedObject.mapIndexed { _, obj ->
                    // for every currency in initial response, request detailed info (with flag)
                    launch {
                        val response = dataSource.getCurrenciesInitial(currencyCode = obj.currencyCode)
                        if (response is Response.Success) {
                            val data = response.data
                            mutex.withLock {
                                val newCurrencyData = CurrencyEntity(
                                    currencyCode = data.currencyCode,
                                    currencyName = data.currencyName,
                                    flagImageUrl = data.flagImage,
                                    // properly use the rates
                                    usdRate = data.rates.ratesMap["usd"] ?: (1/obj.usdRate)
                                )
                                val updated = result.swapElementWithNew(
                                    findOldElement = { it.currencyCode == newCurrencyData.currencyCode },
                                    newElement = newCurrencyData
                                )
                                if (!updated) result.add(newCurrencyData)
                                // save to local storage
                                kStore.addCurrencies(result)
                                send(Response.Success(result.toList()))
                            }
                        }
                    }
                }.joinAll()
                if (result.isEmpty()) send(Response.Failure("List empty"))
            }
        }
    }.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    companion object {
        private fun <T> MutableList<T>.swapElementWithNew(
            findOldElement: (T) -> Boolean,
            newElement: T
        ) : Boolean {
            val oldElement = this.find(findOldElement)
            val i = this.indexOf(oldElement)
            if (i == -1) return false
            this[i] = newElement
            return true
        }
    }
}