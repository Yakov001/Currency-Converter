package data.data_source.local

import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore

expect class KStoreFactory {
    fun createCurrencyStore() : KStore<List<CurrencyDto>>
    fun createLastSelectedCurrenciesStore(): KStore<LastSelected>
}