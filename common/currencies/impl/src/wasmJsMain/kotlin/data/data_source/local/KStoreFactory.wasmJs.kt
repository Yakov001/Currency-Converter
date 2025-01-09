package data.data_source.local

import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

actual class KStoreFactory {
    actual fun createCurrencyStore(): KStore<List<CurrencyDto>> {
        return storeOf(key = "currencies")
    }

    actual fun createLastSelectedCurrenciesStore(): KStore<LastSelected> {
        return storeOf("last_selected_currencies")
    }
}