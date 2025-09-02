package com.projects.base.ui.component.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.projects.base.data.model.Category
import com.projects.base.ui.component.category.CategoryFragment

class HomePagerAdapter(
    fragment: Fragment,
    private val categories: List<Category>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categories.size
    override fun createFragment(position: Int): Fragment {
        return CategoryFragment.newInstance(categories[position].type)
    }
}