package ru.tashkent.stocks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.tashkent.stocks.R
import ru.tashkent.stocks.adapters.FAVOURITE_STOCK_LIST_PAGE_INDEX
import ru.tashkent.stocks.adapters.STOCK_LIST_PAGE_INDEX
import ru.tashkent.stocks.adapters.ViewPagerAdapter
import ru.tashkent.stocks.databinding.FragmentHomeViewPagerBinding
import timber.log.Timber
import java.lang.IndexOutOfBoundsException

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        Timber.d("Hello, World!")

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabText(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabText(position: Int): String {
        return when (position) {
            STOCK_LIST_PAGE_INDEX -> "Stocks"
            FAVOURITE_STOCK_LIST_PAGE_INDEX -> "Favourite"
            else -> throw IndexOutOfBoundsException()
        }
    }
}