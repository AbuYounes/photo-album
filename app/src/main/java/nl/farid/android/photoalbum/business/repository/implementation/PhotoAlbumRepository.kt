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
) : IPhotoAlbumRepository {

    override suspend fun markAsFavorite(album: Album) {
        iPhotoAlbumCacheDataSource.insertFavoriteAlbum(album.toAlbumEntity())
    }

    override suspend fun getAllFavoriteAlbums(): Flow<List<Album>> = flow {
        val list: MutableList<Album> = mutableListOf()
        iPhotoAlbumCacheDataSource.getAllFavoriteAlbumsFlow().collect { albumEntities ->
            list.clear()
            albumEntities.forEach { albumEntity ->
                list.add(albumEntity.toAlbum())
            }
            emit(list)
        }
    }

    override suspend fun deleteAlbum(id: Int) {
        iPhotoAlbumCacheDataSource.deleteAlbum(id)
    }

    override suspend fun getAlbums(): Flow<List<Album>> = flow {
        iPhotoAlbumCacheDataSource.getAllFavoriteAlbumsFlow().collect { albumEntities ->
            val listBHashSet: HashSet<Int> = hashSetOf()
            albumEntities.forEach { albumEntity ->
                listBHashSet.add(albumEntity.id)
            }

            val list: MutableList<Album> = mutableListOf()
            iPhotoAlbumNetworkDataSource.getAlbums().forEach { albumDTO ->
                list.add(albumDTO.toAlbum(listBHashSet.contains(albumDTO.id)))
            }

            val flowList = list.asFlow().toList()
            emit(flowList)
        }
    }

    override suspend fun getPhotosFromAlbum(albumId: Int): List<Photo> {
        val list: MutableList<Photo> = mutableListOf()
        iPhotoAlbumNetworkDataSource.getPhotosFromAlbum(albumId).forEach { photoDTO ->
            list.add(photoDTO.toPhoto())
        }

        return list
    }
}