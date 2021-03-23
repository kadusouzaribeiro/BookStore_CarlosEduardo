package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items (

	val kind : String?,
	val id : String?,
	val etag : String?,
	val selfLink : String?,
	val volumeInfo : VolumeInfo?,
	val saleInfo : SaleInfo?,
	val accessInfo : AccessInfo?,
	val searchInfo : SearchInfo?
)