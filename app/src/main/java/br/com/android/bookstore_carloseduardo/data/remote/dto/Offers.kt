package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Offers (

	val finskyOfferType : Int?,
	val listPrice : ListPrice?,
	val retailPrice : RetailPrice?,
	val giftable : Boolean?
)