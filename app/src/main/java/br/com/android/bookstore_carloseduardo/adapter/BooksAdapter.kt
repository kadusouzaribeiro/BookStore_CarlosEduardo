package br.com.android.bookstore_carloseduardo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.android.bookstore_carloseduardo.GlideApp
import br.com.android.bookstore_carloseduardo.R
import br.com.android.bookstore_carloseduardo.data.local.entity.Book
import br.com.android.bookstore_carloseduardo.databinding.ItemBookBinding
import br.com.android.bookstore_carloseduardo.listener.BookListener

/**
 * Created by Carlos Souza on 20,March,2021
 */
class BooksAdapter(private val booksList: List<Book>, private val listener: BookListener): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.BooksViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksAdapter.BooksViewHolder, position: Int) {
        with(holder) {
            with(booksList[position]) {
                GlideApp.with(holder.itemView.context)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
                    .into(binding.imgBook)
                binding.txtBookTitle.text = name
                holder.itemView.setOnClickListener {
                    listener.onSelectBook(this, it)
                }
            }
        }
    }

    override fun getItemCount() = booksList.size

    inner class BooksViewHolder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root)
}