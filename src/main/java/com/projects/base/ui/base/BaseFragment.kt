package com.projects.base.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!
    abstract fun getViewBinding(): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  _binding?.root ?: getViewBinding().also { _binding = it }.root
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

    open fun initViews(savedInstanceState: Bundle?) { }
    open fun addObservers(savedInstanceState: Bundle?) { }
    open fun initListeners(savedInstanceState: Bundle?) { }
    open fun initData(savedInstanceState: Bundle?) { }
}