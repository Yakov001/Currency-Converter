package di

import domain.ConversionUseCase
import org.koin.dsl.module

fun converterModule() = module {
    single { ConversionUseCase() }
}