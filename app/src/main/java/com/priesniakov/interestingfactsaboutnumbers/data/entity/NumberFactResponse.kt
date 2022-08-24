package com.priesniakov.interestingfactsaboutnumbers.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priesniakov.interestingfactsaboutnumbers.data.database.NUMBER_FACTS_TABLE_NAME

@Entity(tableName = NUMBER_FACTS_TABLE_NAME)
data class NumberFactResponse(
    val found: Boolean,
    @PrimaryKey(autoGenerate = false)
    val number: Int,
    val text: String,
    val type: String
)