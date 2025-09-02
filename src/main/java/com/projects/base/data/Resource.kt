package com.projects.base.data

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(val message: String? = null, errorCode: Int? = null) : Resource<T>(null, errorCode)


    fun whenLoading(callback: (Loading<T>) -> Unit) = apply {
        if (this is Loading) {
            callback(this)
        }
    }

    fun whenError(callback: (Error<T>) -> Unit) = apply {
        if (this is Error) {
            callback(this)
        }
    }

    suspend fun whenSuccess(callback: suspend (Success<T>) -> Unit) = apply {
        if (this is Success) {
            callback(this)
        }
    }

    /**
     * Transform to another resource
     */
    suspend fun <R> map(transform: suspend (T) -> R): Resource<R> {
        return when (this) {
            is Loading -> Loading()
            is Error -> Error(errorCode = errorCode)
            is Success -> Success(data = transform(data!!))
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
        }
    }

    val isSuccess get() = this is Success
    val isError get() = this is Error
    val isLoading get() = this is Loading
}
