package br.com.android.bookstore_carloseduardo

import android.app.Application
import br.com.android.bookstore_carloseduardo.data.local.LocalRepository
import br.com.android.bookstore_carloseduardo.data.local.db.BookDatabase
import br.com.android.bookstore_carloseduardo.data.remote.RemoteRepository
import br.com.android.bookstore_carloseduardo.viewmodel.BookListViewModel
import br.com.android.bookstore_carloseduardo.viewmodel.BooksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Created by Carlos Souza on 20,March,2021
 */
class BookApplication : Application() {

    var modules = module {
        single { BookDatabase.getInstance(context = get()) }
        single { LocalRepository(get()) }
        single { RemoteRepository()}
        viewModel { BooksViewModel(get()) }
        viewModel { BookListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BookApplication)
            modules(modules)
        }
    }
}