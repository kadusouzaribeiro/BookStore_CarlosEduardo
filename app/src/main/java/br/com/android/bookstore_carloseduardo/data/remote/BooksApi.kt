package br.com.android.bookstore_carloseduardo.data.remote

import br.com.android.bookstore_carloseduardo.data.remote.dto.BooksSource
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Carlos Souza on 17,March,2021
 */
interface BooksApi {
    @GET("books/v1/volumes")
    suspend fun getBooks(
            @Query("q") search: String = "android",
            @Query("maxResults") limit: Int,
            @Query("startIndex") offset: Int,
    ): BooksSource
}