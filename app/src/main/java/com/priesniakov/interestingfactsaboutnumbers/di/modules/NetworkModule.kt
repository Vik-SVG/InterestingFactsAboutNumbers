package com.priesniakov.interestingfactsaboutnumbers.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.priesniakov.interestingfactsaboutnumbers.BuildConfig
import com.priesniakov.interestingfactsaboutnumbers.core.network.DefaultInterceptor
import com.priesniakov.interestingfactsaboutnumbers.data.api.NumberFactsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.addInterceptor(DefaultInterceptor())
        builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(NumberFactsApi::class.java) }
}
private const val TIMEOUT_SEC: Long = 10