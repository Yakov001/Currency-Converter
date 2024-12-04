package data.repository

import data.Response
import data.data_source.ktor.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import domain.model.CurrencyEntity

class CurrenciesRepositoryNew(
    private val dataSource: KtorCurrenciesDataSource,
    private val kStore: KStoreDataSource
) {
    suspend fun getCurrencies(): Response<List<CurrencyEntity>> {
        // first, return what we have saved locally, then try to get updates
        val localResponse = kStore.getAllCurrencies()
        if (localResponse != null) return Response.Success(localResponse)

        val remoteResponse = dataSource.getCurrenciesNew()
        if (remoteResponse is Response.Success) {
            val result = remoteResponse.data.conversionRates.ratesMap.filterKeys { key ->
                // todo(): only return currencies that have a flag image
                true
            }.map { rate ->
                CurrencyEntity(
                    currencyCode = rate.key,
                    currencyName = rate.key,
                    flagImageUrl = rate.key,
                    usdRate = rate.value
                )
            }
            return Response.Success(result)
        } else return Response.Failure("unlucky")
    }
}