package data.data_source.local

import data.model.Currency
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<Currency>> {
        return storeOf(key = "currencies")
    }
}