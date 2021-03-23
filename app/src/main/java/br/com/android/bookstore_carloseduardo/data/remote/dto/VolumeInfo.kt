package br.com.android.bookstore_carloseduardo.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VolumeInfo (

	val title : String?,
	val authors : List<String>?,
	val publisher : String?,
	val description : String?,
	val industryIdentifiers : List<IndustryIdentifiers>?,
	val readingModes : ReadingModes?,
	val pageCount : Int?,
	val printType : String?,
	val maturityRating : String?,
	val allowAnonLogging : Boolean?,
	val contentVersion : String?,
	val panelizationSummary : PanelizationSummary?,
	val imageLinks : ImageLinks?,
	val language : String?,
	val previewLink : String?,
	val infoLink : String?,
	val canonicalVolumeLink : String?
)