package di

import ktor.HttpEngineFactory
import org.koin.dsl.module

fun coreModule() = module {
    single<HttpEngineFactory> { HttpEngineFactory() }
}