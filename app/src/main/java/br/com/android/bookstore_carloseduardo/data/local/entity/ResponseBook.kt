package br.com.android.bookstore_carloseduardo.data.local.entity

/**
 * Created by Carlos Souza on 21,March,2021
 */
data class ResponseBook(
    val totalItems: Int,
    val books: List<Book>
)