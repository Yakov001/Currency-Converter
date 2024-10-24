package data.data_source.local

import data.model.Currency
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KStoreDataSource(
    private val store : KStore<List<Currency>>
) {

    fun getCurrencies(): Flow<List<Currency>> = store.updates.map { it ?: emptyList() }

    suspend fun addCurrency(cur : Currency) {
        store.update { nullableList ->
            nullableList?.let { list ->
                list.toMutableList().apply { add(cur) }
            } ?: emptyList()
        }
    }

    suspend fun addCurrencies(curs : List<Currency>) {
        store.delete()
        store.set(curs)
    }
}