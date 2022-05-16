package nl.farid.android.photoalbum.business.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.model.app_model.Album

interface IGetAllAlbumsFromCache {
    suspend fun getAllAlbumsFromCache(): GetAllAlbumsFromCacheResult
}

sealed class GetAllAlbumsFromCacheResult{
    data class Success(val list: Flow<List<Album>>): GetAllAlbumsFromCacheResult()
    data class Error(val e: Exception): GetAllAlbumsFromCacheResult()
}