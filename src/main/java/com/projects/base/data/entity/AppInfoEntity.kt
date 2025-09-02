package com.projects.base.data.entity

import android.content.Context
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.io.FileOutputStream

@Entity(tableName = "app_info")
data class AppInfoEntity(
    @PrimaryKey val packageName: String,
    val appName: String,
    val icon: String,
    var lastOpenTime: Long = 0,
    val isBlocked: Boolean = false,
    val opens: Int = 0,
    val usageHours: Int = 0,
    val totalUsageTime: Long = 0, // Existing field
    val lastFetchedTime: Long = 0 // New field for caching
)

fun PackageInfo.toAppInfoEntity(context: Context): AppInfoEntity {
    val packageManager = context.packageManager
    // `applicationInfo` can be null on newer API levels → fetch a fallback if needed
    val safeAppInfo = this.applicationInfo ?: try {
        packageManager.getApplicationInfo(packageName, 0)
    } catch (e: Exception) {
        null
    }

    val appName = safeAppInfo?.loadLabel(packageManager)?.toString() ?: packageName
    val iconBitmap = try {
        safeAppInfo?.loadIcon(packageManager)?.toBitmap()
    } catch (e: Exception) {
        null
    }
    val iconFile = iconBitmap?.let { bitmapToFile(it, context, packageName) }


    return AppInfoEntity(
        packageName = packageName,
        icon = iconFile?.absolutePath ?: "",
        appName = appName
    )
}

fun bitmapToFile(bitmap: Bitmap, context: Context, packageName: String): File {
    val file = File(context.filesDir, "$packageName.png")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return file
}