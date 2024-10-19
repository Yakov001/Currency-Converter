package di

import org.koin.dsl.KoinAppDeclaration

val resonanseKoinApplication : KoinAppDeclaration = {
    modules(
        coreModule(),
        coinsModule(),
        converterModule()
    )
}