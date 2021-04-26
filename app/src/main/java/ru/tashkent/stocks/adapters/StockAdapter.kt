package ru.tashkent.stocks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.tashkent.stocks.R
import ru.tashkent.stocks.data.remote.Result
import ru.tashkent.stocks.databinding.ItemStockBinding
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.absoluteValue

class StockAdapter @Inject constructor() : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ItemStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]
        val currencySymbol = getCurrencySymbol(stock.currency)
        with(holder.binding) {
            tvStockSymbol.text = stock.symbol
            tvStockName.text = stock.longName
            tvStockPrice.text = "${currencySymbol}%.2f".format(stock.regularMarketPrice)
            val priceChange = "%.2f".format(stock.regularMarketChange.absoluteValue)
            val priceChangePercentage =
                "%.2f".format(stock.regularMarketChangePercent.absoluteValue)
            if (stock.isFavourite) {
                Timber.d("Bind stock icon: ${stock.symbol}, favourite")
                ivFavourite.load(R.drawable.ic_baseline_star_24_favourite)
            } else {
                Timber.d("Bind stock icon: ${stock.symbol}, not favourite")
                ivFavourite.load(R.drawable.ic_baseline_star_24_simple)
            }
            if (stock.regularMarketChange > 0) {
                tvStockPriceChange.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.stock_item_price_change_up
                    )
                )
                tvStockPriceChange.text =
                    "+${currencySymbol}$priceChange ($priceChangePercentage%)"
            } else {
                tvStockPriceChange.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.stock_item_price_change_down
                    )
                )
                tvStockPriceChange.text =
                    "-${currencySymbol}$priceChange ($priceChangePercentage%)"
            }
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.stock_item_gray
                    )
                )
            } else {
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.stock_item_white
                    )
                )
            }

            ivFavourite.setOnClickListener {
                onFavouriteClickListener?.let { click ->
                    click(stock, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    class StockViewHolder(
        val binding: ItemStockBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var onFavouriteClickListener: ((Result, Int) -> Unit)? = null

    private fun getCurrencySymbol(currency: String): String {
        return "$"
    }

    private val callback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, callback)

    var stocks: List<Result>
        set(value) = differ.submitList(value)
        get() = differ.currentList
}