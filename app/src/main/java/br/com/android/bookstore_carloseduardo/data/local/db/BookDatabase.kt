package br.com.android.bookstore_carloseduardo.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.android.bookstore_carloseduardo.data.local.dao.BookDao
import br.com.android.bookstore_carloseduardo.data.local.entity.Book

/**
 * Created by Carlos Souza on 19,March,2021
 */
@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun BookDao(): BookDao

    companion object {

        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(BookDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "BookDB.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}