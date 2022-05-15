package nl.farid.android.photoalbum.business.repository.implementation

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.data.network.abstraction.IPhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.app_model.Photo
import javax.inject.Inject

@FlowPreview
class PhotoAlbumRepository
@Inject constructor(
    private val iPhotoAlbumCacheDataSource: IPhotoAlbumCacheDataSource,
    private val iPhotoAlbumNetworkDataSource: IPhotoAlbumNetworkDataSource
): IPhotoAlbumRepository{

    override suspend fun markAsFavorite(album: Album) {
        iPhotoAlbumCacheDataSource.insertFavoriteAlbum(album.toAlbumEntity())
    }

    override suspend fun getAllFavoriteAlbums(): List<Album> {
        val list: MutableList<Album> = mutableListOf()

        iPhotoAlbumCacheDataSource.getAllFavoriteAlbums().flattenToList().forEach {
            list.add(it.toAlbum())
        }

        return list
    }


    override suspend fun deleteAlbum(id: Int) {
       iPhotoAlbumCacheDataSource.deleteAlbum(id)
    }

    override suspend fun getAlbums(): List<Album> {
        val list: MutableList<Album> = mutableListOf()

        iPhotoAlbumNetworkDataSource.getAlbums().forEach {
            list.add(it.toAlbum())
        }

        return list
    }


    override suspend fun getPhotosFromAlbum(albumId: Int): List<Photo> {
        val list: MutableList<Photo> = mutableListOf()

        iPhotoAlbumNetworkDataSource.getPhotosFromAlbum(albumId).forEach {
            list.add(it.toPhoto())
        }

        return list
    }

}

@FlowPreview
suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()