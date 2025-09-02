package com.projects.base.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface BaseRepository: CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}