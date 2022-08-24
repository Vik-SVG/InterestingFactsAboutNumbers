package com.priesniakov.interestingfactsaboutnumbers.di.modules

import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCase
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCaseImpl
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumbersHistoryUseCase
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumbersHistoryUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<NumberFactsUseCase> { NumberFactsUseCaseImpl(get()) }
    factory<NumbersHistoryUseCase> { NumbersHistoryUseCaseImpl(get()) }
}