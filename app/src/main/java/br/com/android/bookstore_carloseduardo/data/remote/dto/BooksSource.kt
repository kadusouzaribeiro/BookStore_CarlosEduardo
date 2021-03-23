package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksSource (
	val kind : String?,
	val totalItems : Int?,
	val items : List<Items>
)