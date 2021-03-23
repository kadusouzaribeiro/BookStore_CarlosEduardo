package br.com.android.bookstore_carloseduardo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import br.com.android.bookstore_carloseduardo.GlideApp
import br.com.android.bookstore_carloseduardo.viewmodel.BooksViewModel
import br.com.android.bookstore_carloseduardo.R
import br.com.android.bookstore_carloseduardo.data.local.entity.Book
import br.com.android.bookstore_carloseduardo.databinding.BookDetailsFragmentBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BookDetailsFragment : Fragment() {

    private lateinit var binding: BookDetailsFragmentBinding
    private val viewModel: BooksViewModel by sharedViewModel()
    private var book: Book? = null

    companion object {
        fun newInstance() = BookDetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.book_details_fragment, container, false).also {
            binding = BookDetailsFragmentBinding.bind(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { it ->
            book = it.getSerializable("B") as Book
            book?.let { b ->
                viewModel.getSingleBook(b.id)
            }
        }

        viewModel.observeBook().removeObservers(this)
        viewModel.observeBook().observe(viewLifecycleOwner, {
            it.data?.let { b ->
                if (b.id == book?.id) {
                    book?.favorite = b.favorite
                }
            }
            setDetails()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                Navigation.findNavController(requireView()).popBackStack()
            }
            R.id.exit -> requireActivity().finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDetails() {
        binding.apply {
            book?.let { b->
                GlideApp.with(this@BookDetailsFragment)
                    .load(b.image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
                    .into(imgBook)
                txtBookTitle.text = b.name
                txtBookAuthors.text = if (b.authors == "null") "" else b.authors
                txtBookDescription.text = b.description
                if (b.saleInfo == "NOT_FOR_SALE") {
                    cvBuy.isEnabled = false
                    imgBookBuy.visibility = View.GONE
                    txtBookBuy.text = b.saleInfo
                } else {
                    imgBookBuy.visibility = View.VISIBLE
                    txtBookBuy.text = getString(R.string.buy)
                    cvBuy.setOnClickListener {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(b.buyLink)
                        startActivity(i)
                    }
                }
                if (b.favorite) {
                    imgBookFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_yes))
                    imgBookFavorite.setOnClickListener {
                        b.favorite = false
                        viewModel.unfavoriteBook(b)
                        imgBookFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_no))
                    }
                } else {
                    imgBookFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_no))
                    imgBookFavorite.setOnClickListener {
                        b.favorite = true
                        viewModel.favoriteBook(b)
                        imgBookFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_yes))
                    }
                }
            }
        }
    }
}