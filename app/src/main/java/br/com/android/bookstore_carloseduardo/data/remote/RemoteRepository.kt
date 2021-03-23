package br.com.android.bookstore_carloseduardo.data.remote

import androidx.lifecycle.MutableLiveData
import br.com.android.bookstore_carloseduardo.data.Resource
import br.com.android.bookstore_carloseduardo.data.local.entity.Book
import br.com.android.bookstore_carloseduardo.data.local.entity.ResponseBook
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * Created by Carlos Souza on 20,March,2021
 */
class RemoteRepository {

    val booksList = MutableLiveData<Resource<ResponseBook>>()

    private val exception = CoroutineExceptionHandler { _, ex ->
        booksList.postValue(Resource.error(null, ex.message ?: "Error loading API"))
    }

    fun getBooksList(limit: Int = 10, offset: Int = 0) = CoroutineScope(IO).launch(exception) {
        booksList.postValue(Resource.loading(null))
        BooksRetrofit.bookApi.getBooks(limit = limit, offset = offset).let { bs ->
            val total = bs.totalItems ?: 0
            with(bs.items) {
                val resBooks = this.map {
                    Book(
                        id = it.id ?: "",
                        name = it.volumeInfo?.title ?: "",
                        saleInfo = it.saleInfo?.saleability ?: "",
                        authors = it.volumeInfo?.authors.toString(),
                        description = it.volumeInfo?.description ?: "",
                        buyLink = it.saleInfo?.buyLink ?: "",
                        image = it.volumeInfo?.imageLinks?.smallThumbnail ?: "",
                        favorite = false
                    )
                }
                val responseBook = ResponseBook(
                    totalItems = total,
                    books = resBooks
                )

                booksList.postValue(Resource.success(responseBook))
            }

        }
    }
}

