package com.projects.base.di

import com.projects.base.data.datasource.local.database.AppDatabase
import com.projects.base.data.datasource.local.dao.WallpaperDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    // Database
    single { AppDatabase.getDatabase(androidContext()) }

    // DAOs
    single { get<AppDatabase>().appInfoDao() }
    single { get<AppDatabase>().focusSessionDao() }
    single { get<AppDatabase>().blockEventDao() }
    single<WallpaperDao> { get<AppDatabase>().wallpaperDao() }
}
