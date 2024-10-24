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
        val path = Path("${context.filesDir}/currencies.json")
        return storeOf(file = path)
    }
}