package nl.farid.android.photoalbum.util

import nl.farid.android.photoalbum.model.app_model.Album
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumManager
@Inject constructor(){
    lateinit var mAlbum: Album

    fun setAlbum(album: Album){
        mAlbum = album
    }

    fun getAlbum(): Album {
        return mAlbum
    }
}