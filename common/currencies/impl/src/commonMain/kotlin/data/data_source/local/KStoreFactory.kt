package data.data_source.local

import data.model.Currency
import io.github.xxfast.kstore.KStore

expect class KStoreFactory {
    fun createCurrencyStore() : KStore<List<Currency>>
}