package nl.farid.android.photoalbum.business.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.model.app_model.Album

interface IGetAlbums {
    suspend fun getAlbums(): GetAlbumResult
}

sealed class GetAlbumResult{
    data class Success(val list: Flow<List<Album>>): GetAlbumResult()
    data class Error(val e: Exception): GetAlbumResult()
}