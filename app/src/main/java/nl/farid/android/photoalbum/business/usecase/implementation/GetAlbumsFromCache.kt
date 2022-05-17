package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.GetAlbumsFromCacheResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetAlbumsFromCache
import java.lang.Exception
import javax.inject.Inject

class GetAlbumsFromCache
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IGetAlbumsFromCache{

    override suspend fun getAllAlbumsFromCache(): GetAlbumsFromCacheResult {
        return try {
            GetAlbumsFromCacheResult.Success(iPhotoAlbumRepository.getAllFavoriteAlbumsNoFlow())
        } catch (e: Exception){
            GetAlbumsFromCacheResult.Error(e)
        }
    }
}