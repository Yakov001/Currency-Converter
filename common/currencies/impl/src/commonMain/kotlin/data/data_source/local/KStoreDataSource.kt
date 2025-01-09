package data.data_source.local

import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore
import domain.model.CurrencyEntity
import utils.toDto
import utils.toEntity

class KStoreDataSource(
    private val currenciesStore: KStore<List<CurrencyDto>>,
    private val lastSelectedCurrenciesStore: KStore<LastSelected>
) {

    suspend fun getAllCurrencies(): List<CurrencyEntity>? =
        currenciesStore.get()?.map { it.toEntity() }

    suspend fun addCurrency(cur: CurrencyEntity) {
        currenciesStore.update { nullableList ->
            nullableList?.let { list ->
                list.toMutableList().apply { add(cur.toDto()) }
            } ?: emptyList()
        }
    }

    suspend fun addCurrencies(curs: List<CurrencyEntity>) {
        // TODO() not drop old results, just replace them with new ones
        currenciesStore.update { oldList ->
            curs.map { it.toDto() }
        }
    }

    suspend fun getLastSelectedCurrencies(): LastSelected? = lastSelectedCurrenciesStore.get()

    suspend fun updateLastSelectedCurrencies(
        fromCurrency: CurrencyEntity,
        toCurrency: CurrencyEntity
    ) {
        lastSelectedCurrenciesStore.update {
            LastSelected(fromCurrency.toDto(), toCurrency.toDto())
        }
    }
}