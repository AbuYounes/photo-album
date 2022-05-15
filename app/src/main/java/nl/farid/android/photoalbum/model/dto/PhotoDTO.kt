package nl.farid.android.photoalbum.model.dto

import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.app_model.Photo

data class PhotoDTO(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) {

    fun toPhoto(): Photo {
        return Photo(
            albumId = albumId,
            id = id,
            thumbnailUrl = thumbnailUrl,
            title = title,
            url = url

        )
    }

    fun toAlbumList(listDTO: List<PhotoDTO>): List<Photo> {
        val list: MutableList<Photo> = mutableListOf()
        listDTO.forEach {
            list.add(it.toPhoto())
        }

        return list
    }

}