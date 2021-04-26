package ru.tashkent.stocks.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.tashkent.stocks.adapters.StockAdapter
import ru.tashkent.stocks.databinding.FragmentFavouriteStocksBinding
import ru.tashkent.stocks.other.UiObserver
import ru.tashkent.stocks.other.snackbar
import ru.tashkent.stocks.ui.viewmodels.StockViewModel
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteStocksFragment : Fragment() {

    @Inject
    lateinit var stockAdapter: StockAdapter

    private val viewModel: StockViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouriteStocksBinding.inflate(inflater, container, false)
        setupRecyclerView(binding)
        subscribeToObservers()

        viewModel.getFavouriteStocks()

        return binding.root
    }

    private fun setupRecyclerView(binding: FragmentFavouriteStocksBinding) {
        binding.rvFavouriteStocks.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val itemDecorator = object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 15
                }
            }
            addItemDecoration(itemDecorator)
        }
    }

    private fun subscribeToObservers() {
        viewModel.favouriteStocks.observe(viewLifecycleOwner, UiObserver(
            onError = {
                snackbar(it)
            },
            onLoading = {

            }
        ) { stocks ->
            stockAdapter.stocks = stocks
        })
    }
}