package br.com.android.bookstore_carloseduardo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.android.bookstore_carloseduardo.data.Resource
import br.com.android.bookstore_carloseduardo.data.local.LocalRepository
import br.com.android.bookstore_carloseduardo.data.local.entity.Book

class BooksViewModel(private val localRepository: LocalRepository) : ViewModel() {

    fun observeFavoriteBooks(): LiveData<Resource<List<Book>>> = localRepository.listFavoriteBooks

    fun observeBook(): LiveData<Resource<Book>> = localRepository.book

    fun getListFavorites() = localRepository.getListBooks()

    fun getSingleBook(id: String) = localRepository.getBook(id)

    fun favoriteBook(book: Book) {
        localRepository.insertBook(book)
    }

    fun unfavoriteBook(book: Book) {
        localRepository.deleteBook(book)
    }
}