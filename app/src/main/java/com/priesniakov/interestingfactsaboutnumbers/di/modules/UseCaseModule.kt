package com.priesniakov.interestingfactsaboutnumbers.di.modules

import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCase
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<NumberFactsUseCase> { NumberFactsUseCaseImpl(get()) }
}