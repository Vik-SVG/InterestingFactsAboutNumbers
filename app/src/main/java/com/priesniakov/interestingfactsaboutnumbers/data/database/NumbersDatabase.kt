package com.priesniakov.interestingfactsaboutnumbers.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse


const val INTERESTING_FACTS_DATABASE_NAME = "InterestingFactsDatabaseName"

@Database(
    entities = [NumberFactResponse::class],
    version = 1,
    exportSchema = false,
)
abstract class NumbersDatabase : RoomDatabase() {

    abstract fun numbersFactsDao(): NumbersFactsDao

    companion object {
        @Volatile
        private var instance: NumbersDatabase? = null

        fun getInstance(context: Context): NumbersDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NumbersDatabase {
            return Room.databaseBuilder(
                context,
                NumbersDatabase::class.java,
                INTERESTING_FACTS_DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}