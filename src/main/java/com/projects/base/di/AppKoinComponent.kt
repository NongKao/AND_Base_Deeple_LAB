package com.projects.base.di

import com.projects.base.BaseApplicationKit
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

interface AppKoinComponent: KoinComponent {
    override fun getKoin(): Koin {
        return BaseApplicationKit.koin.koin
    }
}