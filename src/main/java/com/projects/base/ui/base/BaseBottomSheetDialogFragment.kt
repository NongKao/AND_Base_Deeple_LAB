package com.projects.base.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {
    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    open fun initViews(savedInstanceState: Bundle?) { }
    open fun addObservers(savedInstanceState: Bundle?) { }
    open fun initListeners(savedInstanceState: Bundle?) { }
    open fun initData(savedInstanceState: Bundle?) { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
        addObservers(savedInstanceState)
        initListeners(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
