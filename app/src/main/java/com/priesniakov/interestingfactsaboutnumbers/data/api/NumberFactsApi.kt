package com.priesniakov.interestingfactsaboutnumbers.data.api

import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NumberFactsApi {

    @GET("/random/trivia")
    suspend fun getRandomNumberFact(): Response<NumberFactResponse>

    @GET("/{number}/trivia")
    suspend fun getExactNumberFact(@Path("number") number: Int): Response<NumberFactResponse>
}