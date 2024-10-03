package utils

import android.util.Log

actual object Log {
    actual fun d(text: String, tag: String) {
        Log.d("TAG", text)
    }
}