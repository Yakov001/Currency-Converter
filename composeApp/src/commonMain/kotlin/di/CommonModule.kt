package di

import data.RemoteRepository
import data.RemoteRepositoryImpl
import data.ktor.KtorInstance
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun commonModule() = module {
    singleOf(::KtorInstance)
}

fun remoteModule() = module {
    single<RemoteRepository> { RemoteRepositoryImpl(get()) }
}

val resonanseKoinApplication : KoinAppDeclaration = {
    modules(
        commonModule(),
        remoteModule(),
        coreModule(),
        coinsModule()
    )
}