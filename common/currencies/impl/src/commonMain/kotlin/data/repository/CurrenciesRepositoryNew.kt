@file:OptIn(ExperimentalResourceApi::class)

package data.repository

import currency_converter.common.currencies.impl.generated.resources.Res
import data.Response
import data.data_source.local.KStoreDataSource
import data.data_source.remote.KtorCurrenciesDataSource
import data.model.CurrencyDto
import domain.model.CurrencyEntity
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utils.toEntity

class CurrenciesRepositoryNew(
    private val remoteDataSource: KtorCurrenciesDataSource,
    private val localDataSource: KStoreDataSource
) {

    suspend fun getCurrencies(): Response<List<CurrencyEntity>> {
        // first, fetch local data, if success -> return
        getLocalCurrencies { return it }

        // if no local data, fetch remote
        return getUpdatedCurrencies()
    }

    suspend fun getUpdatedCurrencies(): Response<List<CurrencyEntity>> {
        val remoteResponse = remoteDataSource.getCurrenciesNew()
        if (remoteResponse is Response.Success) {
            val defaultCurs = getDefaultCurrencies()
            val result = remoteResponse.data.conversionRates.ratesMap
                .filterKeys { curCode ->
                    defaultCurs.find { it.currencyCode == curCode } != null
                }
                .map { remoteCur ->
                    val defaultCur = defaultCurs.find { it.currencyCode == remoteCur.key }!!
                    CurrencyEntity(
                        currencyCode = defaultCur.currencyCode,
                        currencyName = defaultCur.currencyName,
                        flagImageUrl = defaultCur.flagImageUrl,
                        fromUsd = remoteCur.value
                    )
                }
            updateLocalCurrencies(result)
            return Response.Success(result)
        } else return Response.Failure(
            message = (remoteResponse as? Response.Failure)?.message ?: DEFAULT_ERROR_MESSAGE
        )
    }

    private suspend fun updateLocalCurrencies(currencies: List<CurrencyEntity>) {
        localDataSource.addCurrencies(currencies)
    }

    private suspend inline fun getLocalCurrencies(
        onSuccess: (Response<List<CurrencyEntity>>) -> Unit
    ) {
        localDataSource.getAllCurrencies()?.takeIf { it.isNotEmpty() }?.let { onSuccess(Response.Success(it)) }
    }

    private suspend fun getDefaultCurrencies(): List<CurrencyEntity> {
        val defaultCurrenciesJson = Res.readBytes(FILE_PATH).decodeToString()
        val json = Json {
            ignoreUnknownKeys = true
            allowStructuredMapKeys = true
        }
        return json.decodeFromString<List<CurrencyDto>>(defaultCurrenciesJson).map { it.toEntity() }
    }

    companion object {
        const val FILE_PATH = "files/default_currencies.json"
        const val DEFAULT_ERROR_MESSAGE = "Unknown Error"
    }
}