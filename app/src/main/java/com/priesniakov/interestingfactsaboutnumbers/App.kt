package com.priesniakov.interestingfactsaboutnumbers

import android.app.Application
import com.priesniakov.interestingfactsaboutnumbers.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, viewModelsModule, networkModule, dataModule, useCaseModule)
        }
    }
}