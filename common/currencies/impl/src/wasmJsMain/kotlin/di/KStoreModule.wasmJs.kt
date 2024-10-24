package di

import data.data_source.local.KStoreFactory
import org.koin.dsl.module

actual fun kStoreFactoryModule() = module {
    single { KStoreFactory() }
}