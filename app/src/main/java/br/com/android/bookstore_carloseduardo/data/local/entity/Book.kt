package br.com.android.bookstore_carloseduardo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.android.bookstore_carloseduardo.data.remote.dto.BooksSource
import java.io.Serializable

/**
 * Created by Carlos Souza on 19,March,2021
 */
@Entity(tableName = "books")
data class Book(
        @PrimaryKey
        val id: String,
        val name: String,
        val saleInfo: String,
        val authors: String,
        val description: String,
        val buyLink: String,
        val image: String,
        var favorite: Boolean = false,
): Serializable
