package com.projects.base.ui.component.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.projects.base.databinding.ActivitySplashBinding
import com.projects.base.ui.base.BaseActivity
import com.projects.base.ui.component.MainActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val vm: SplashViewModel by viewModel()

    override fun getDataBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vm.preload()
        lifecycleScope.launch {
            val delayJob = async { delay(3000) }
            delayJob.await()
            vm.wallpapers.value.forEach { item ->
                Glide.with(applicationContext)
                    .load(item.url)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .preload()
            }
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}