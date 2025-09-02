package com.projects.base.data.datasource.remote

import com.projects.base.data.model.PicsumImage
import retrofit2.Response

interface WallpaperRemoteDataSource {
    suspend fun getImages(page: Int, limit: Int): Response<List<PicsumImage>>
    suspend fun getImageInfo(id: String): Response<PicsumImage>
}

