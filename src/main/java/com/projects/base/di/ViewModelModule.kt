package com.projects.base.di

import com.projects.base.ui.component.category.CategoryViewModel
import com.projects.base.ui.component.home.HomeViewModel
import com.projects.base.ui.component.splash.SplashViewModel
import com.projects.base.ui.component.detail.ImageDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { ImageDetailViewModel(get()) }
}