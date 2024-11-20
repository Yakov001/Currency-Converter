package data.data_source.local

import data.model.Currency
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import utils.Log

class KStoreDataSource(
    private val store : KStore<List<Currency>>
) {

    suspend fun getAllCurrencies() : List<Currency>? = store.get()

    suspend fun addCurrency(cur : Currency) {
        store.update { nullableList ->
            nullableList?.let { list ->
                list.toMutableList().apply { add(cur) }
            } ?: emptyList()
        }
    }

    suspend fun addCurrencies(curs : List<Currency>) {
        // TODO() not drop old results, just replace them with new ones
        store.update { oldList ->
            curs
        }
    }
}