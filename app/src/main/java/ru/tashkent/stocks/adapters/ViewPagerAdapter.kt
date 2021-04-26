package ru.tashkent.stocks.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.tashkent.stocks.ui.fragments.FavouriteStocksFragment
import ru.tashkent.stocks.ui.fragments.StocksFragment
import timber.log.Timber
import java.lang.IndexOutOfBoundsException


const val STOCK_LIST_PAGE_INDEX = 0
const val FAVOURITE_STOCK_LIST_PAGE_INDEX = 1

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        STOCK_LIST_PAGE_INDEX to { StocksFragment() },
        FAVOURITE_STOCK_LIST_PAGE_INDEX to { FavouriteStocksFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        tabFragmentsCreators[position]?.invoke()?.let {
            Timber.d("Create fragment: ${it::class.java.name}")
        }
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}