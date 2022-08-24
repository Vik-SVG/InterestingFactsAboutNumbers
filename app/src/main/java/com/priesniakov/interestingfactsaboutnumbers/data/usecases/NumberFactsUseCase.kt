package com.priesniakov.interestingfactsaboutnumbers.data.usecases

import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceError
import com.priesniakov.interestingfactsaboutnumbers.data.common.NumberFact
import com.priesniakov.interestingfactsaboutnumbers.data.repositories.NumberFactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


interface NumberFactsUseCase {
    operator fun invoke(): Flow<NumberFact>
    operator fun invoke(number: Int): Flow<NumberFact>
}

class NumberFactsUseCaseImpl(
    private val repository: NumberFactsRepository,
) : NumberFactsUseCase {

    override fun invoke() = repository.getRandomNumberFact()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(number: Int): Flow<NumberFact> =
        repository.getCashedNumber(number).flatMapLatest {
            channelFlow {
                when (it) {
                    is ResourceError -> repository.getExactNumberFact(number)
                        .collectLatest { data ->
                            send(data)
                        }
                    else -> send(it)
                }
            }.flowOn(Dispatchers.IO)
        }
}