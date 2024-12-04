package di

import data.repository.CurrenciesRepository
import data.data_source.ktor.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import org.koin.core.qualifier.named
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
        KtorCurrenciesDataSource(
            httpClientNew = get(qualifier = named(NEW_API_KOIN_QUALIFIER)),
            httpClient = get(qualifier = named(OLD_API_KOIN_QUALIFIER))
        )
    }
    single {
        CurrenciesRepository(dataSource = get(), kStore = get())
    }
}