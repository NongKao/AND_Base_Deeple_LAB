package com.projects.base.domain.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

class WallpaperSetterImpl(
    private val context: Context,
    private val wallpaperManager: WallpaperManager
) : WallpaperSetter {

	override suspend fun setHome(url: String) {
		withContext(Dispatchers.IO) {
			val bitmap = loadBitmap(url)
			val scaled = centerCropScale(bitmap, desiredWidth(), desiredHeight())
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				wallpaperManager.setBitmap(scaled, null, true, WallpaperManager.FLAG_SYSTEM)
			} else {
				wallpaperManager.setBitmap(scaled)
			}
		}
	}

	override suspend fun setLock(url: String) {
		withContext(Dispatchers.IO) {
			val bitmap = loadBitmap(url)
			val scaled = centerCropScale(bitmap, desiredWidth(), desiredHeight())
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				wallpaperManager.setBitmap(scaled, null, true, WallpaperManager.FLAG_LOCK)
			} else {
				throw UnsupportedOperationException("Lock screen wallpaper not supported on this Android version")
			}
		}
	}

	override suspend fun setBoth(url: String) {
		withContext(Dispatchers.IO) {
			val bitmap = loadBitmap(url)
			val scaled = centerCropScale(bitmap, desiredWidth(), desiredHeight())
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				wallpaperManager.setBitmap(
					scaled,
					null,
					true,
					WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
				)
			} else {
				wallpaperManager.setBitmap(scaled)
			}
		}
	}

	private suspend fun loadBitmap(url: String): Bitmap = withContext(Dispatchers.IO) {
		Glide.with(context).asBitmap().load(url).submit().get()
	}

	private fun desiredWidth(): Int = max(wallpaperManager.desiredMinimumWidth, 1080)
	private fun desiredHeight(): Int = max(wallpaperManager.desiredMinimumHeight, 1920)

	private fun centerCropScale(src: Bitmap, targetW: Int, targetH: Int): Bitmap {
		val srcW = src.width
		val srcH = src.height
		val scale = max(targetW.toFloat() / srcW, targetH.toFloat() / srcH)
		val scaledW = (srcW * scale).toInt()
		val scaledH = (srcH * scale).toInt()
		val scaled = Bitmap.createScaledBitmap(src, scaledW, scaledH, true)
		val x = (scaledW - targetW) / 2
		val y = (scaledH - targetH) / 2
		return Bitmap.createBitmap(scaled, x.coerceAtLeast(0), y.coerceAtLeast(0), targetW.coerceAtMost(scaledW), targetH.coerceAtMost(scaledH))
	}
}
