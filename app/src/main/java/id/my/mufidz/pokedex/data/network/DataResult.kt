package id.my.mufidz.pokedex.data.network

sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Failure(val error: Throwable) : DataResult<Nothing>()
}
