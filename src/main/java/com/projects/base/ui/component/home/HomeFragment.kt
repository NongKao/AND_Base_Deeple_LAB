package com.projects.base.ui.component.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.projects.base.data.model.Category
import com.projects.base.databinding.FragmentHomeBinding
import com.projects.base.ui.base.BaseFragment
import com.projects.base.ui.component.category.CategoryFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val vm: HomeViewModel by viewModel()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        setupViewPager()
    }

    override fun addObservers(savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    if (state.categories.isNotEmpty()) setupViewPager(state.categories)
                }
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vm.handleIntent(HomeIntent.LoadCategories)
    }

    private fun setupViewPager(categories: List<Category> = listOf()) {
        if (!::viewPagerAdapter.isInitialized) {
            viewPagerAdapter = ViewPagerAdapter(this)
            binding.viewPager.adapter = viewPagerAdapter
            // Submit data before attaching TabLayoutMediator to ensure titles are available
            viewPagerAdapter.submit(categories)
            TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, pos ->
                tab.text = viewPagerAdapter.getTitle(pos)
            }.attach()
        } else  {
            viewPagerAdapter.submit(categories)
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val categories: MutableList<Category> = mutableListOf()

        private fun buildDiffCallback(newCategories: List<Category>) = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = categories.size
            override fun getNewListSize(): Int = newCategories.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return categories[oldItemPosition].id == newCategories[newItemPosition].id
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = categories[oldItemPosition]
                val newItem = newCategories[newItemPosition]
                return oldItem == newItem
            }
        }

        override fun getItemCount(): Int = categories.size

        override fun createFragment(position: Int): Fragment {
            return CategoryFragment.newInstance(categories[position].type)
        }

        fun getTitle(position: Int): String = categories.getOrNull(position)?.name ?: ""

        fun submit(newCategories: List<Category>) {
            val diff = DiffUtil.calculateDiff(buildDiffCallback(newCategories))
            categories.clear()
            categories.addAll(newCategories)
            diff.dispatchUpdatesTo(this)
        }

        override fun getItemId(position: Int): Long = categories[position].id.hashCode().toLong()

        override fun containsItem(itemId: Long): Boolean =
            categories.any { it.id.hashCode().toLong() == itemId }
    }
}