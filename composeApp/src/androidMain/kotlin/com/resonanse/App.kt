package com.resonanse

import android.app.Application
import di.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }
}