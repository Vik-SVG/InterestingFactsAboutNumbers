package com.priesniakov.interestingfactsaboutnumbers.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import kotlinx.coroutines.flow.Flow

const val NUMBER_FACTS_TABLE_NAME = "NumbersFactsTableName"

@Dao
interface NumbersFactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumberFact(vararg exceptionLog: NumberFactResponse)

    @Query("SELECT * FROM $NUMBER_FACTS_TABLE_NAME")
    fun getAllFacts(): Flow<List<NumberFactResponse>>

    @Query("DELETE FROM $NUMBER_FACTS_TABLE_NAME")
    suspend fun deleteAllFacts()

    @Query("SELECT * FROM $NUMBER_FACTS_TABLE_NAME WHERE number = :number")
    suspend fun getFactByNumber(number: Int): NumberFactResponse?

    @Query("DELETE FROM $NUMBER_FACTS_TABLE_NAME WHERE number = :number")
    suspend fun deleteFactByNumber(number: Int)
}