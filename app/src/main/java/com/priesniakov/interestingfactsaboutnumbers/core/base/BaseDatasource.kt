package com.priesniakov.interestingfactsaboutnumbers.core.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priesniakov.interestingfactsaboutnumbers.core.data.*
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResults(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null)
                    return ResourceSuccess(body)
            }
            return ResourceError("Network call failed brcause: ${response.errorBody()?.string()} ")
        } catch (e: Exception) {
            return ResourceError(e.message ?: e.toString())
        }
    }


    protected suspend inline fun <T, reified E> getResultsWithSerializedError(
        call: suspend () -> Response<T>
    ): ApiCallResult<T, E> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null)
                    return SUCCESS(body)

            }
            val gson = Gson()
            val type = object : TypeToken<E>() {}.type
            val errorType: E? = gson.fromJson(response.errorBody()?.charStream(), type)

            return ERROR(errorType, "Call has failed because: "+errorType.toString())

        } catch (e: Exception) {

            return ERROR(null, e.message.toString())
        }
    }
}