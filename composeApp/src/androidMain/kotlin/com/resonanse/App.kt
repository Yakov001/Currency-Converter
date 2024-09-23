package com.resonanse

import android.app.Application
import di.resonanseKoinApplication
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(resonanseKoinApplication)
    }
}