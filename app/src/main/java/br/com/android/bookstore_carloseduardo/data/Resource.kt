package br.com.android.bookstore_carloseduardo.data

/**
 * Created by Carlos Souza on 20,March,2021
 */
data class Resource<out T>(val status: ResponseStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = ResponseStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ResponseStatus.ERROR, data = null, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = ResponseStatus.LOADING, data = null, message = null)
    }
}

enum class ResponseStatus { SUCCESS, ERROR, LOADING }