package com.projects.base.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.projects.base.R

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun getDataBinding(): T

    open fun initViews(savedInstanceState: Bundle?) { }
    open fun addObservers(savedInstanceState: Bundle?) { }
    open fun initListeners(savedInstanceState: Bundle?) { }
    open fun initData(savedInstanceState: Bundle?) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = getDataBinding()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews(savedInstanceState)
        addObservers(savedInstanceState)
        initListeners(savedInstanceState)
        initData(savedInstanceState)
    }
}