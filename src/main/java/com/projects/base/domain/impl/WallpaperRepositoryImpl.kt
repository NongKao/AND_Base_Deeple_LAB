package com.projects.base.domain.impl

import com.projects.base.data.Resource
import com.projects.base.data.datasource.local.dao.WallpaperDao
import com.projects.base.data.datasource.remote.WallpaperRemoteDataSource
import com.projects.base.data.entity.WallpaperCacheEntity
import com.projects.base.data.model.Category
import com.projects.base.data.model.CategoryType
import com.projects.base.data.model.WallpaperItem
import com.projects.base.domain.repository.BaseRepository
import com.projects.base.domain.repository.WallpaperRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WallpaperRepositoryImpl(
    private val remoteDataSource: WallpaperRemoteDataSource,
    private val wallpaperDao: WallpaperDao
) : WallpaperRepository, BaseRepository {

    override suspend fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val categories = listOf(
                Category("1", "AI ART", CategoryType.AI_ART),
                Category("2", "24 HOUR", CategoryType.HOUR_24),
                Category("3", "VIDEO", CategoryType.VIDEO)
            )
            emit(Resource.Success(categories))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getWallpapersByCategory(
        categoryType: CategoryType,
        page: Int
    ): Flow<Resource<List<WallpaperItem>>> = callbackFlow {
        send(Resource.Loading())

        val categoryKey = categoryType.name
        var emittedAny = false

        // Observe local cache first and emit updates
        wallpaperDao.getByCategoryPage(categoryKey, page).let {
            send(Resource.Success(it.map { it.toItem() }))
        }
        // Decide whether to refresh from remote based on TTL
        launch(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getImages(page = page, limit = 20)
                if (response.isSuccessful) {
                    val body = response.body().orEmpty()
                    val entities = body.map { picsum ->
                        WallpaperCacheEntity(
                            id = picsum.id + categoryKey,
                            categoryType = categoryKey,
                            page = page,
                            author = picsum.author,
                            url = "https://picsum.photos/id/${picsum.id}/400/600",
                            downloadUrl = "https://picsum.photos/id/${picsum.id}/1080/1920.jpg",
                            width = picsum.width,
                            height = picsum.height,
                            updatedAt = System.currentTimeMillis()
                        )
                    }
                    wallpaperDao.upsertAll(entities)
                    send(entities.map { it.toItem() }.let { Resource.Success(it) })
                }
            } catch (e: Exception) {
                if (!emittedAny) send(Resource.Error(e.message ?: "Network error"))
            }
        }
        awaitClose { }
    }

    private fun WallpaperCacheEntity.toItem(): WallpaperItem =
        WallpaperItem(
            id = id,
            url = url,
            downloadUrl = downloadUrl,
            author = author,
            width = width,
            height = height
        )
}