package com.projects.base.data.diff

import com.projects.base.data.entity.AppInfoEntity

data class AppInfoEntityDiff(
    val packageNameChanged: Boolean,
    val appNameChanged: Boolean,
    val iconChanged: Boolean,
    val lastOpenTimeChanged: Boolean,
    val isBlockedChanged: Boolean,
    val opensChanged: Boolean,
    val usageHoursChanged: Boolean,
    val totalUsageTimeChanged: Boolean,
    val lastFetchedTimeChanged: Boolean
) {
    fun hasDifference(): Boolean {
        return packageNameChanged || appNameChanged || iconChanged || lastOpenTimeChanged ||
                isBlockedChanged || opensChanged || usageHoursChanged || totalUsageTimeChanged ||
                lastFetchedTimeChanged
    }
}

fun AppInfoEntity.diff(other: AppInfoEntity): AppInfoEntityDiff {
    return AppInfoEntityDiff(
        packageNameChanged = this.packageName != other.packageName,
        appNameChanged = this.appName != other.appName,
        iconChanged = this.icon != other.icon,
        lastOpenTimeChanged = this.lastOpenTime != other.lastOpenTime,
        isBlockedChanged = this.isBlocked != other.isBlocked,
        opensChanged = this.opens != other.opens,
        usageHoursChanged = this.usageHours != other.usageHours,
        totalUsageTimeChanged = this.totalUsageTime != other.totalUsageTime,
        lastFetchedTimeChanged = this.lastFetchedTime != other.lastFetchedTime
    )
}

fun List<AppInfoEntity>.hasDifferences(other: List<AppInfoEntity>): Boolean {
    if (this.size != other.size) return true

    for (i in this.indices) {
        if (this[i].diff(other[i]).hasDifference()) {
            return true
        }
    }
    return false
}