package di

import data.data_source.local.KStoreFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun kStoreFactoryModule() = module {
    single { KStoreFactory(context = androidContext()) }
}