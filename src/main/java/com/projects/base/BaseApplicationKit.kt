package com.projects.base

import android.annotation.SuppressLint
import android.content.Context
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

@SuppressLint("StaticFieldLeak")
object BaseApplicationKit {
    lateinit var koin: KoinApplication
    lateinit var context: Context
    private val modules = mutableListOf<Module>()

    fun initKoin(app: Context) {
        koin = GlobalContext.getKoinApplicationOrNull() ?: startKoin {
            modules(modules)
        }
        context = app
    }

    fun setKoinModules(module: List<Module>) {
        modules.clear()
        modules.addAll(module)
    }
}