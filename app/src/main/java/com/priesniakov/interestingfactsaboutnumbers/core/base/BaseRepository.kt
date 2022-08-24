package com.priesniakov.interestingfactsaboutnumbers.core.base

import com.priesniakov.interestingfactsaboutnumbers.core.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


open class BaseRepository {

    fun <RESPONSE, ERR> performNetworkCallWithErrorEntity(
        networkCall: suspend () -> ApiCallResult<RESPONSE, ERR>,
    ): Flow<ApiCallResult<RESPONSE, ERR>> =
        flow {
            emit(LOADING)
            val networkResponse = networkCall.invoke()
            if (networkResponse is SUCCESS) {
                emit(networkResponse)
            } else if (networkResponse is ERROR) {
                emit(ERROR(networkResponse.errorData, networkResponse.message))
            }
        }.flowOn(Dispatchers.IO)


    fun <R> performNetworkCall(
        networkCall: suspend () -> Resource<R>,
    ): Flow<Resource<R>> =
        flow {
            emit(ResourceLoading)
            val networkResponse = networkCall.invoke()

            if (networkResponse is ResourceSuccess) {
                emit(networkResponse)

            } else if (networkResponse is ResourceError)
                emit(ResourceError(networkResponse.message))
        }.flowOn(Dispatchers.IO)

    fun <R> performNetworkCallWithCashing(
        saveData: suspend (data: R) -> Unit,
        networkCall: suspend () -> Resource<R>,
    ): Flow<Resource<R>> =
        flow {
            emit(ResourceLoading)

            val networkResponse = networkCall.invoke()

            if (networkResponse is ResourceSuccess) {
                emit(networkResponse)
                saveData(networkResponse.data)
            } else if (networkResponse is ResourceError)
                emit(ResourceError(networkResponse.message))
        }.flowOn(Dispatchers.IO)

    fun <R> getCashedData(getCash: suspend () -> R?): Flow<Resource<R>> =
        flow {
            emit(ResourceLoading)
            val cash = getCash()
            if (cash != null) {
                emit(ResourceSuccess(cash))
            } else {
                emit(ResourceError(message = "Entity not cashed"))
            }
        }.flowOn(Dispatchers.IO)

}
