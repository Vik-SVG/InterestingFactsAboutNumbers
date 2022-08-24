package com.priesniakov.interestingfactsaboutnumbers.di.modules

import com.priesniakov.interestingfactsaboutnumbers.data.datasources.NumberFactsDatasource
import com.priesniakov.interestingfactsaboutnumbers.data.datasources.NumberFactsDatasourceImpl
import com.priesniakov.interestingfactsaboutnumbers.data.repositories.NumberFactsRepository
import com.priesniakov.interestingfactsaboutnumbers.data.repositories.NumberFactsRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<NumberFactsDatasource> { NumberFactsDatasourceImpl(get()) }
    single<NumberFactsRepository> { NumberFactsRepositoryImpl(get(), get()) }
}