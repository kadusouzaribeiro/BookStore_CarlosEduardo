package br.com.android.bookstore_carloseduardo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.android.bookstore_carloseduardo.data.Resource
import br.com.android.bookstore_carloseduardo.data.local.entity.ResponseBook
import br.com.android.bookstore_carloseduardo.data.remote.RemoteRepository

class BookListViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    fun observeBooks(): LiveData<Resource<ResponseBook>> {
        return remoteRepository.booksList
    }

    fun getBooksList(limit: Int = 10, offset: Int = 0) {
        remoteRepository.getBooksList(limit, offset)
    }
}