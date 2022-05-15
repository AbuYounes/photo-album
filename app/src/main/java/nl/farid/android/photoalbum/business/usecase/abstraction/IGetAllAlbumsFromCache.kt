package nl.farid.android.photoalbum.business.usecase.abstraction

import nl.farid.android.photoalbum.model.app_model.Album

interface IGetAllAlbumsFromCache {
    suspend fun getAllAlbumsFromCache(): GetAllAlbumsFromCacheResult
}

sealed class GetAllAlbumsFromCacheResult{
    data class Success(val list: List<Album>): GetAllAlbumsFromCacheResult()
    data class Error(val e: Exception): GetAllAlbumsFromCacheResult()
}