package com.priesniakov.interestingfactsaboutnumbers.data.repositories

import com.priesniakov.interestingfactsaboutnumbers.core.base.BaseRepository
import com.priesniakov.interestingfactsaboutnumbers.data.common.NumberFact
import com.priesniakov.interestingfactsaboutnumbers.data.database.NumbersFactsDao
import com.priesniakov.interestingfactsaboutnumbers.data.datasources.NumberFactsDatasource
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import kotlinx.coroutines.flow.Flow

interface NumberFactsRepository {

    fun getRandomNumberFact(): Flow<NumberFact>
    fun getExactNumberFact(number: Int): Flow<NumberFact>
    fun getCashedNumber(number: Int): Flow<NumberFact>
    fun getNumbersHistory(): Flow<List<NumberFactResponse>>
}

class NumberFactsRepositoryImpl(
    private val datasource: NumberFactsDatasource,
    private val dao: NumbersFactsDao
) : BaseRepository(),
    NumberFactsRepository {

    override fun getRandomNumberFact() = performNetworkCallWithCashing(
        networkCall = datasource::getRandomNumberFact,
        saveData = { dao.insertNumberFact(it) }
    )

    override fun getExactNumberFact(number: Int) =
        performNetworkCallWithCashing(
            networkCall = { datasource.getExactNumberFact(number) },
            saveData = { dao.insertNumberFact(it) }
        )

    override fun getCashedNumber(number: Int) = getCashedData { dao.getFactByNumber(number) }

    override fun getNumbersHistory(): Flow<List<NumberFactResponse>> = dao.getAllFacts()


}