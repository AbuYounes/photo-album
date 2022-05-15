package nl.farid.android.photoalbum.business.usecase.abstraction

import nl.farid.android.photoalbum.model.app_model.Photo

interface IGetPhotosFromAlbum {
    suspend fun getPhotoFromAlbum(albumId: Int): GetPhotosFromAlbumResult
}

sealed class GetPhotosFromAlbumResult{
    data class Success(val list: List<Photo>): GetPhotosFromAlbumResult()
    data class Error(val e: Exception): GetPhotosFromAlbumResult()
}