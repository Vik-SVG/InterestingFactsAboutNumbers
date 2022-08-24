package com.priesniakov.interestingfactsaboutnumbers.core.data

sealed class ApiCallResult<out T, out E>

data class SUCCESS<out T : Any>(val data: T) : ApiCallResult<T, Nothing>()
data class ERROR<E>(val errorData: E?, val message: String) : ApiCallResult<Nothing, E>()
object LOADING : ApiCallResult<Nothing, Nothing>()

sealed class Resource<out T>

data class ResourceSuccess<out T : Any>(val data: T) : Resource<T>()
data class ResourceError<E>(val message: String) : Resource<E>()
object ResourceLoading : Resource<Nothing>()
object ResourceIdle : Resource<Nothing>()
