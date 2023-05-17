package itmo.skymap.utils


sealed class Data<out T>{
    data class Error(val message: String): Data<Nothing>()
    data class Ok<T>(val data: T): Data<T>()
}
