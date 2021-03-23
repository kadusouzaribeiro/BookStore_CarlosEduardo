package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessInfo (

	val country : String?,
	val viewability : String?,
	val embeddable : Boolean?,
	val publicDomain : Boolean?,
	val textToSpeechPermission : String?,
	val epub : Epub?,
	val pdf : Pdf?,
	val webReaderLink : String?,
	val accessViewStatus : String?,
	val quoteSharingAllowed : Boolean?
)