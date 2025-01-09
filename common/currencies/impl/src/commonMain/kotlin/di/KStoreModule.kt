package di

import data.data_source.local.KStoreFactory
import data.model.CurrencyDto
import data.model.LastSelected
import io.github.xxfast.kstore.KStore
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun kStoreFactoryModule() : Module

fun kStoreDataSourceModule() = module {
    single<KStore<List<CurrencyDto>>> {
        get<KStoreFactory>().createCurrencyStore()
    }
    single<KStore<LastSelected>>(
        qualifier = named("last_selected_currencies")
    ) {
        get<KStoreFactory>().createLastSelectedCurrenciesStore()
    }
}