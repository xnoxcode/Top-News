package epm.xnox.topnews.core.common

sealed class Result<out T> {
    data object Loading: Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error (val message: String) : Result<Nothing>()
}