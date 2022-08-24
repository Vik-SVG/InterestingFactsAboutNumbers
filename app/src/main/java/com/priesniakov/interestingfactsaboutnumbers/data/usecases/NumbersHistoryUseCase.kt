package com.priesniakov.interestingfactsaboutnumbers.data.usecases

import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.data.repositories.NumberFactsRepository
import kotlinx.coroutines.flow.Flow

interface NumbersHistoryUseCase {
    operator fun invoke(): Flow<List<NumberFactResponse>>
}

class NumbersHistoryUseCaseImpl(private val repository: NumberFactsRepository) :
    NumbersHistoryUseCase {
    override fun invoke() = repository.getNumbersHistory()
}
