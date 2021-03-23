package br.com.android.bookstore_carloseduardo.data.remote

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Carlos Souza on 17,March,2021
 */
object BooksRetrofit {

    private const val BASE_URL = "https://www.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun getClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        val interceptor =
            HttpLoggingInterceptor { message ->
                Log.d("Retrofit", message)
            }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return clientBuilder.addInterceptor(interceptor).build()
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getClient())
            .baseUrl(BASE_URL)
            .build()
    }

    val bookApi: BooksApi by lazy {
        retrofit().create(BooksApi::class.java)
    }
}