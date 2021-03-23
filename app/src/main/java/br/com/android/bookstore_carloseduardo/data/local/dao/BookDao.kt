package br.com.android.bookstore_carloseduardo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.android.bookstore_carloseduardo.data.local.entity.Book

/**
 * Created by Carlos Souza on 19,March,2021
 */
@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Book): Long?

    @Update
    suspend fun update(data: Book): Int?

    @Delete
    suspend fun delete(data: Book)

    @Query("SELECT * FROM books")
    fun getAll(): List<Book>?

    @Query("SELECT * FROM books WHERE id = :id")
    fun getById(id: String): Book?
}