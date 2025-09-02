package com.projects.base.ui.component

import android.os.Bundle
import com.projects.base.R
import com.projects.base.databinding.ActivityMainBinding
import com.projects.base.ui.base.BaseActivity
import com.projects.base.ui.component.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }
    }
}