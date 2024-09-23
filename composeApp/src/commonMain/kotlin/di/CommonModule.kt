package di

import data.ktor.KtorInstance
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun commonModule() = module {
    singleOf(::KtorInstance)
}

val koinApplication : KoinAppDeclaration = {
    modules(commonModule())
}

fun startKoin() = startKoin {
    koinApplication
}
