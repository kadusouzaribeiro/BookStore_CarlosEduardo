package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListPrice (

	val amount : Double?,
	val currencyCode : String?
)