package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.GetAllAlbumsFromCacheResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetAllAlbumsFromCache
import javax.inject.Inject

class GetAllAlbumsFromCache
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IGetAllAlbumsFromCache{

    override suspend fun getAllAlbumsFromCache(): GetAllAlbumsFromCacheResult {
        return try {
            GetAllAlbumsFromCacheResult.Success(iPhotoAlbumRepository.getAllFavoriteAlbums())
        } catch (e: Exception){
            GetAllAlbumsFromCacheResult.Error(e)
        }
    }
}