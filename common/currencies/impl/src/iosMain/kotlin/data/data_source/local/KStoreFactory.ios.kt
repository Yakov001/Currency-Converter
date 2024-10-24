package data.data_source.local

import data.model.Currency
import io.github.xxfast.kstore.KStore

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<Currency>> {
        TODO("Not yet implemented")
    }
}