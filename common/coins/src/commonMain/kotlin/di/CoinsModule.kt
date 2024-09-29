package di

import data.CoinRepository
import data.ktor.KtorCoinDataSource
import org.koin.dsl.module

fun coinsModule() = module {
    single {
        KtorCoinDataSource(httpClient = get())
    }
    single {
        CoinRepository(dataSource = get())
    }
}