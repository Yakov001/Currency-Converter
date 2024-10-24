package di

import data.repository.CurrenciesRepository
import data.data_source.ktor.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import org.koin.dsl.module

fun currenciesModule() = module {
    includes(
        kStoreFactoryModule(),
        kStoreDataSourceModule()
    )
    single {
        KStoreDataSource(
            store = get()
        )
    }
    single {
        KtorCurrenciesDataSource(httpClient = get())
    }
    single {
        CurrenciesRepository(dataSource = get(), kStore = get())
    }
}