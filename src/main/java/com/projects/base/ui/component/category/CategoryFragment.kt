package com.projects.base.ui.component.category

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.projects.base.data.model.CategoryType
import com.projects.base.databinding.FragmentCategoryBinding
import com.projects.base.ui.adapter.WallpaperAdapter
import com.projects.base.ui.base.BaseFragment
import com.projects.base.ui.component.detail.ImageDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val vm: CategoryViewModel by viewModel()
    private lateinit var adapter: WallpaperAdapter

    override fun getViewBinding(): FragmentCategoryBinding = FragmentCategoryBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        val type = requireArguments().getSerializable(KEY_TYPE) as CategoryType
        setupRecycler()
        vm.handleIntent(CategoryIntent.LoadImages(type))
    }

    override fun addObservers(savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    adapter.submit(state.images)
                }
            }
        }
    }

    private fun setupRecycler() {
        adapter = WallpaperAdapter { item ->
            val i = Intent(requireContext(), ImageDetailActivity::class.java)
            i.putExtra(ImageDetailActivity.EXTRA_IMAGE_URL, item.downloadUrl)
            startActivity(i)
        }
        binding.recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recycler.adapter = adapter
    }

    companion object {
        private const val KEY_TYPE = "key_type"
        fun newInstance(type: CategoryType): CategoryFragment {
            val f = CategoryFragment()
            f.arguments = bundleOf(KEY_TYPE to type)
            return f
        }
    }
}