package data.data_source.local

import data.model.CurrencyDto
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<CurrencyDto>> {
        return storeOf(key = "currencies")
    }
}