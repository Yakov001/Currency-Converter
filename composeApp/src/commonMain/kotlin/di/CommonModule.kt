package di

import org.koin.dsl.KoinAppDeclaration

fun resonanseKoinAppDeclaration(block : KoinAppDeclaration? = null) : KoinAppDeclaration = {
    block?.invoke(this)
    modules(
        coreModule(),
        currenciesModule(),
        converterModule()
    )
}