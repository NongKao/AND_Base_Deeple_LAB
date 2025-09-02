package com.projects.base.data.datasource.remote.service

import com.projects.base.data.model.PicsumImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicsumService {
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 30
    ): Response<List<PicsumImage>>
    
    @GET("id/{id}/info")
    suspend fun getImageInfo(
        @Path("id") id: String
    ): Response<PicsumImage>
}