package com.projects.base.data.datasource.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingTypeService {
    @GET("trendingType")
    suspend fun fetchTrendingType(@Query("version") version: String): Response<Int>
}