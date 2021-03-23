package br.com.android.bookstore_carloseduardo.data.local

import androidx.lifecycle.MutableLiveData
import br.com.android.bookstore_carloseduardo.data.Resource
import br.com.android.bookstore_carloseduardo.data.local.db.BookDatabase
import br.com.android.bookstore_carloseduardo.data.local.entity.Book
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 * Created by Carlos Souza on 20,March,2021
 */
class LocalRepository(private val database: BookDatabase) {

    val listFavoriteBooks = MutableLiveData<Resource<List<Book>>>()
    val book = MutableLiveData<Resource<Book>>()

    private val exceptionList = CoroutineExceptionHandler { _, ex ->
        listFavoriteBooks.postValue(Resource.error(null, ex.message ?: "Error loading list of books"))
    }

    private val exception = CoroutineExceptionHandler { _, ex ->
        book.postValue(Resource.error(null, ex.message ?: "Error recovering book"))
    }

    fun insertBook(book: Book) {
        CoroutineScope(IO).launch {
            database.BookDao().insert(book)
        }
    }

    fun deleteBook(book: Book) {
        CoroutineScope(IO).launch {
            database.BookDao().delete(book)
        }
    }

    fun getListBooks() = CoroutineScope(IO).launch(exceptionList) {
        listFavoriteBooks.postValue(Resource.loading(null))
        val result = database.BookDao().getAll()
        result?.let {
            listFavoriteBooks.postValue(Resource.success(it))
        }
    }

    fun getBook(id: String) = CoroutineScope(IO).launch(exception) {
        book.postValue(Resource.loading(null))
        val result = database.BookDao().getById(id)
        result?.let {
            book.postValue(Resource.success(result))
        }
    }
}