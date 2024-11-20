package data.data_source.local

import android.content.Context
import data.model.Currency
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

actual class KStoreFactory (
    private val context : Context
) {
    actual fun createCurrencyStore(): KStore<List<Currency>> {
        val appStorage = Path(context.filesDir.path)
        return storeOf<List<Currency>>(
            file = Path(path = "$appStorage/$FILE_NAME"),
            default = emptyList()
        )
    }
    companion object {
        private const val FILE_NAME = "currencies.json"
    }
}