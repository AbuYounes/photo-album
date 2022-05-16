package nl.farid.android.photoalbum.business.usecase.abstraction

interface IDeleteAlbum {
    suspend fun deleteAlbum(albumId: Int): DeleteAlbumResult
}

sealed class DeleteAlbumResult{
    object Success: DeleteAlbumResult()
    data class Error(val e: Exception): DeleteAlbumResult()
}