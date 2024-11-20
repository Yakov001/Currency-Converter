package data.data_source.local

import data.model.CurrencyDto
import io.github.xxfast.kstore.KStore

class KStoreDataSource(
    private val store : KStore<List<CurrencyDto>>
) {

    suspend fun getAllCurrencies() : List<CurrencyDto>? = store.get()

    suspend fun addCurrency(cur : CurrencyDto) {
        store.update { nullableList ->
            nullableList?.let { list ->
                list.toMutableList().apply { add(cur) }
            } ?: emptyList()
        }
    }

    suspend fun addCurrencies(curs : List<CurrencyDto>) {
        // TODO() not drop old results, just replace them with new ones
        store.update { oldList ->
            curs
        }
    }
}