package data.data_source.local

import data.model.CurrencyDto
import io.github.xxfast.kstore.KStore
import domain.model.CurrencyEntity
import utils.toDto
import utils.toEntity

class KStoreDataSource(
    private val store : KStore<List<CurrencyDto>>
) {

    suspend fun getAllCurrencies() : List<CurrencyEntity>? = store.get()?.map { it.toEntity() }

    suspend fun addCurrency(cur : CurrencyEntity) {
        store.update { nullableList ->
            nullableList?.let { list ->
                list.toMutableList().apply { add(cur.toDto()) }
            } ?: emptyList()
        }
    }

    suspend fun addCurrencies(curs : List<CurrencyEntity>) {
        // TODO() not drop old results, just replace them with new ones
        store.update { oldList ->
            curs.map { it.toDto() }
        }
    }
}