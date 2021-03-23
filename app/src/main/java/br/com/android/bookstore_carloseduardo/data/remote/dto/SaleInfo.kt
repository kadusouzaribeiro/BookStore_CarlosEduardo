package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaleInfo (

	val country : String?,
	val saleability : String?,
	val isEbook : Boolean?,
	val listPrice : ListPrice?,
	val retailPrice : RetailPrice?,
	val buyLink : String?,
	val offers : List<Offers>?
)