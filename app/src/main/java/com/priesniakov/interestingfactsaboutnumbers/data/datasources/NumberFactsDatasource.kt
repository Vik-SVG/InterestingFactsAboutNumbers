package com.priesniakov.interestingfactsaboutnumbers.data.datasources

import com.priesniakov.interestingfactsaboutnumbers.core.base.BaseDataSource
import com.priesniakov.interestingfactsaboutnumbers.data.api.NumberFactsApi
import com.priesniakov.interestingfactsaboutnumbers.data.common.NumberFact

interface NumberFactsDatasource {

    suspend fun getExactNumberFact(number: Int): NumberFact
    suspend fun getRandomNumberFact(): NumberFact
}

class NumberFactsDatasourceImpl(private val api: NumberFactsApi) : NumberFactsDatasource,
    BaseDataSource() {

    override suspend fun getRandomNumberFact() = getResults { api.getRandomNumberFact() }

    override suspend fun getExactNumberFact(number: Int) =
        getResults { api.getExactNumberFact(number) }
}