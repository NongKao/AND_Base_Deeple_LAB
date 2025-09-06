package com.projects.base.di

import com.projects.blreathe.data.repository.AppInfoRepository
import com.projects.blreathe.data.repository.BlockEventRepository
import com.projects.blreathe.data.repository.BlockEventRepositoryImpl
import com.projects.blreathe.data.repository.FocusSessionRepository
import com.projects.base.domain.repository.FocusSessionRepositoryImpl
import com.projects.base.data.datasource.remote.ServiceGenerator
import com.projects.base.data.datasource.remote.service.PicsumService
import com.projects.base.data.datasource.remote.WallpaperRemoteDataSource
import com.projects.base.data.datasource.remote.impl.WallpaperRemoteDataSourceImpl
import com.projects.base.domain.impl.WallpaperRepositoryImpl
import com.projects.base.domain.repository.WallpaperRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import android.app.WallpaperManager
import com.projects.base.domain.wallpaper.WallpaperSetter
import com.projects.base.domain.wallpaper.WallpaperSetterImpl

internal val repositoryModule = module {
    // Service Generator
    single { ServiceGenerator() }

    // Services
    single<PicsumService> { get<ServiceGenerator>().createService(PicsumService::class.java) }

    // DataSources
    single<WallpaperRemoteDataSource> { WallpaperRemoteDataSourceImpl(get()) }

    // Repositories
    single<FocusSessionRepository> { FocusSessionRepositoryImpl(get()) }
    single<BlockEventRepository> { BlockEventRepositoryImpl(get()) }
    single<WallpaperRepository> { WallpaperRepositoryImpl(get(), get()) }

    // Wallpaper
    single { WallpaperManager.getInstance(androidContext()) }
    single<WallpaperSetter> { WallpaperSetterImpl(androidContext(), get()) }
}