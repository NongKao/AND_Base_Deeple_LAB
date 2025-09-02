package com.projects.base.data.datasource.remote.impl

import com.projects.base.data.datasource.remote.WallpaperRemoteDataSource
import com.projects.base.data.model.PicsumImage
import com.projects.base.data.datasource.remote.service.PicsumService
import retrofit2.Response

class WallpaperRemoteDataSourceImpl(
    private val picsumService: PicsumService
) : WallpaperRemoteDataSource {

    override suspend fun getImages(page: Int, limit: Int): Response<List<PicsumImage>> {
        return picsumService.getImages(page, limit)
    }

    override suspend fun getImageInfo(id: String): Response<PicsumImage> {
        return picsumService.getImageInfo(id)
    }
}