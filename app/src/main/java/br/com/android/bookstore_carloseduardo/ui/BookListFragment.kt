package br.com.android.bookstore_carloseduardo.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.android.bookstore_carloseduardo.R
import br.com.android.bookstore_carloseduardo.adapter.BooksAdapter
import br.com.android.bookstore_carloseduardo.data.ResponseStatus
import br.com.android.bookstore_carloseduardo.data.local.entity.Book
import br.com.android.bookstore_carloseduardo.databinding.BookListFragmentBinding
import br.com.android.bookstore_carloseduardo.listener.BookListener
import br.com.android.bookstore_carloseduardo.viewmodel.BookListViewModel
import br.com.android.bookstore_carloseduardo.viewmodel.BooksViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BookListFragment : Fragment(), BookListener {

    private lateinit var binding: BookListFragmentBinding

    private val viewModel: BookListViewModel by sharedViewModel()
    private val viewModelBook: BooksViewModel by sharedViewModel()

    private lateinit var booksAdapter: BooksAdapter
    var loading = false
    var limit = 20
    var offset = 0
    var totalBooks = 0

    private val bookList: MutableList<Book> = mutableListOf()

    companion object {
        fun newInstance() = BookListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.book_list_fragment, container, false).also {
        binding = BookListFragmentBinding.bind(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.getBooksList(limit)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.filterFav -> {
                viewModelBook.getListFavorites()
                return true
            }
            R.id.filterAll -> {
                viewModel.getBooksList(limit)
                return true
            }
            R.id.exit -> requireActivity().finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        viewModel.observeBooks().observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.LOADING -> {
                    showLoading()
                }
                ResponseStatus.SUCCESS -> {
                    it.data?.let { it1 ->
                        if (totalBooks == 0)
                            totalBooks = it1.totalItems
                        it1.books.forEach { b ->
                            if (!bookList.contains(b))
                                bookList.add(b)
                        }

                        if (bookList.size < totalBooks) {
                            if (loading) {
                                booksAdapter.notifyDataSetChanged()
                                binding.clProgressView.visibility = View.GONE
                                loading = false
                            } else {
                                setBooksList(bookList)
                            }
                        }
                    }
                }
                ResponseStatus.ERROR -> {
                    showError(it.message ?: "Error")
                }
            }
        })

        viewModelBook.observeFavoriteBooks().observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.LOADING -> {
                    showLoading()
                }
                ResponseStatus.SUCCESS -> {
                    it.data?.let { it1 -> setBooksList(it1) }
                }
                ResponseStatus.ERROR -> {
                    showError(it.message ?: "Error")
                }
            }
        })
    }

    private fun setBooksList(bookList: List<Book>) {
        booksAdapter = BooksAdapter(bookList, this)
        binding.rvBooksList.adapter = booksAdapter
        binding.clProgressView.visibility = View.GONE
        val lManager = binding.rvBooksList.layoutManager as GridLayoutManager

        binding.rvBooksList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!loading) {
                    val totalItem = lManager.itemCount
                    val lastCompletelyVisibleItem = lManager.findLastCompletelyVisibleItemPosition()

                    if (!loading && lastCompletelyVisibleItem == totalItem - 1) {
                        loading = true
                        if (limit < 40)
                            limit += 20

                        if (offset > 0)
                            offset += 20

                        if (limit == 40 && offset == 0)
                            offset = limit

                        viewModel.getBooksList(limit, offset)
                    }
                }
            }
        })
    }

    private fun showLoading() {
        binding.clProgressView.visibility = View.VISIBLE
    }

    private fun showError(msg: String) {
        binding.clProgressView.visibility = View.GONE
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Error")
            setMessage(msg)
            setPositiveButton("OK") { _: DialogInterface, _: Int -> }
            show()
        }
    }

    override fun onSelectBook(book: Book, v: View) {
        val bundle = Bundle()
        bundle.putSerializable("B", book)
        Navigation.findNavController(v).navigate(R.id.action_bookList_to_bookDetails, bundle)
    }
}