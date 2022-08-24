package com.priesniakov.interestingfactsaboutnumbers.di.modules


import com.priesniakov.interestingfactsaboutnumbers.data.database.NumbersDatabase
import org.koin.dsl.module


val appModule = module {

    single { NumbersDatabase.getInstance(get()) }
    single { get<NumbersDatabase>().numbersFactsDao() }
}


