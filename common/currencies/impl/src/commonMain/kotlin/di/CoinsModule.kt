package di

import data.repository.CurrenciesRepository
import data.data_source.remote.KtorCurrenciesDataSource
import data.data_source.local.KStoreDataSource
import data.repository.CurrenciesRepositoryNew
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun currenciesModule() = module {
    includes(
        kStoreFactoryModule(),
        kStoreDataSourceModule()
    )
    single {
        KtorCurrenciesDataSource(
            httpClientNew = get(qualifier = named(NEW_API_KOIN_QUALIFIER)),
            httpClient = get(qualifier = named(OLD_API_KOIN_QUALIFIER))
        )
    }
    singleOf(::KStoreDataSource)
    singleOf(::CurrenciesRepository)
    singleOf(::CurrenciesRepositoryNew)
}