package com.projects.base.ui.component.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.projects.base.databinding.ActivityImageDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collectLatest
import com.projects.base.ui.base.BaseActivity

class ImageDetailActivity : BaseActivity<ActivityImageDetailBinding>() {

    private val viewModel: ImageDetailViewModel by viewModel()

    override fun getDataBinding(): ActivityImageDetailBinding = ActivityImageDetailBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        val url = intent.getStringExtra(EXTRA_IMAGE_URL) ?: return
        Glide.with(this).load(url).into(binding.image)
        binding.btnDownload.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.btnSetWallpaper.setOnClickListener {
            SetWallpaperBottomSheet.newInstance().apply {
                onHome = { viewModel.handleIntent(ImageDetailIntent.SetAsHome(url)) }
                onLock = { viewModel.handleIntent(ImageDetailIntent.SetAsLock(url)) }
                onBoth = { viewModel.handleIntent(ImageDetailIntent.SetAsBoth(url)) }
            }.show(supportFragmentManager, "SetWallpaperBottomSheet")
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                state.message?.let { Toast.makeText(this@ImageDetailActivity, it, Toast.LENGTH_SHORT).show() }
                state.error?.let { Toast.makeText(this@ImageDetailActivity, it, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }
}