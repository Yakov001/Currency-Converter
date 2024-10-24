package di

import data.data_source.local.KStoreFactory
import data.model.Currency
import io.github.xxfast.kstore.KStore
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun kStoreFactoryModule() : Module

fun kStoreDataSourceModule() = module {
    single<KStore<List<Currency>>> {
        get<KStoreFactory>().createCurrencyStore()
    }
}