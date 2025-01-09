package data.data_source.local

import android.content.Context
import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

actual class KStoreFactory (
    private val context : Context
) {
    actual fun createCurrencyStore(): KStore<List<CurrencyDto>> {
        val appStorage = Path(context.filesDir.path)
        return storeOf<List<CurrencyDto>>(
            file = Path(path = "$appStorage/$CURRENCIES_FILE_NAME"),
            default = emptyList()
        )
    }

    actual fun createLastSelectedCurrenciesStore(): KStore<LastSelected> {
        val appStorage = Path(context.filesDir.path)
        return storeOf<LastSelected>(
            file = Path(path = "$appStorage/$LAST_SELECTED_CURRENCIES_FILE_NAME")
        )
    }

    companion object {
        private const val CURRENCIES_FILE_NAME = "currencies.json"
        private const val LAST_SELECTED_CURRENCIES_FILE_NAME = "last_selected_currencies.json"
    }

}