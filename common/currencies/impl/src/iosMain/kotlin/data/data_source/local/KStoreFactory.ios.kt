package data.data_source.local

import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<CurrencyDto>> {
        TODO("Not yet implemented")
    }

    actual fun createLastSelectedCurrenciesStore(): KStore<LastSelected> {
        TODO("Not yet implemented")
    }
}