package nl.farid.android.photoalbum.business.usecase.abstraction

import nl.farid.android.photoalbum.model.app_model.Album

interface IGetAlbumsFromCache {
    suspend fun getAllAlbumsFromCache(): GetAlbumsFromCacheResult
}

sealed class GetAlbumsFromCacheResult{
    data class Success(val list: List<Album>): GetAlbumsFromCacheResult()
    data class Error(val e: Exception): GetAlbumsFromCacheResult()
}