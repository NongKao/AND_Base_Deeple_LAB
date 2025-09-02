package com.projects.base.ui.component.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projects.base.databinding.BottomsheetSetWallpaperBinding
import com.projects.base.ui.base.BaseBottomSheetDialogFragment

class SetWallpaperBottomSheet : BaseBottomSheetDialogFragment<BottomsheetSetWallpaperBinding>() {

    var onHome: (() -> Unit)? = null
    var onLock: (() -> Unit)? = null
    var onBoth: (() -> Unit)? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): BottomsheetSetWallpaperBinding =
        BottomsheetSetWallpaperBinding.inflate(inflater, container, false)

    override fun initListeners(savedInstanceState: Bundle?) {
        binding.btnHome.setOnClickListener {
            dismiss()
            onHome?.invoke()
        }
        binding.btnLock.setOnClickListener {
            dismiss()
            onLock?.invoke()
        }
        binding.btnBoth.setOnClickListener {
            dismiss()
            onBoth?.invoke()
        }
        binding.btnClose.setOnClickListener { dismiss() }
    }

    companion object {
        fun newInstance(): SetWallpaperBottomSheet = SetWallpaperBottomSheet()
    }
}
