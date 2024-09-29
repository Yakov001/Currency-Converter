package utils

import android.util.Log

actual object Log {
    actual fun d(tag: String, text: String) {
        Log.d("TAG", "text")
    }
}