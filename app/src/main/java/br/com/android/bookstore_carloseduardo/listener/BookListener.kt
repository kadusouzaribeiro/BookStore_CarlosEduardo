package br.com.android.bookstore_carloseduardo.listener

import android.view.View
import br.com.android.bookstore_carloseduardo.data.local.entity.Book

/**
 * Created by Carlos Souza on 20,March,2021
 */
interface BookListener {
    fun onSelectBook(book: Book, v: View)
}