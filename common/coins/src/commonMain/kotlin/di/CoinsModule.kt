package di

import data.ktor.KtorCoinDataSource
import org.koin.dsl.module

fun coinsModule() = module {
    single { KtorCoinDataSource(httpClient = get()) }
}