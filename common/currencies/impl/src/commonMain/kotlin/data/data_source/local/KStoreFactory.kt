package data.data_source.local

import data.model.CurrencyDto
import io.github.xxfast.kstore.KStore

expect class KStoreFactory {
    fun createCurrencyStore() : KStore<List<CurrencyDto>>
}