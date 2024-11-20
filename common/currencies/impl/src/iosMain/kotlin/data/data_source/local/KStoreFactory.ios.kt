package data.data_source.local

import data.model.CurrencyDto
import io.github.xxfast.kstore.KStore

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<CurrencyDto>> {
        TODO("Not yet implemented")
    }
}