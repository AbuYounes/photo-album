package nl.farid.android.photoalbum.business.usecase.abstraction

import nl.farid.android.photoalbum.model.app_model.Album

interface IMarkAlbumAsFavorite {
    suspend fun markAsFavorite(album: Album): MarkAlbumAsFavoriteResult
}

sealed class MarkAlbumAsFavoriteResult{
    object Success: MarkAlbumAsFavoriteResult()
    data class Error(val e: Exception): MarkAlbumAsFavoriteResult()
}