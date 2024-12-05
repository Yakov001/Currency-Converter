package com.currency_converter

import android.app.Application
import di.resonanseKoinAppDeclaration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            resonanseKoinAppDeclaration {
                androidContext(this@MainApplication)
            }.invoke(this)
        }
    }
}