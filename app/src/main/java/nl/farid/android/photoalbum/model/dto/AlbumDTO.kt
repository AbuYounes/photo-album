package nl.farid.android.photoalbum.model.dto

import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.entity.AlbumEntity

data class AlbumDTO(
    val id: Int,
    val title: String,
    val userId: Int
) {

    fun toAlbum(): Album {
        return Album(
            id = id,
            title = title,
            userId = userId
        )
    }

    fun toAlbumList(listDTO: List<AlbumDTO>): List<Album> {
        val list: MutableList<Album> = mutableListOf()
        listDTO.forEach {
            list.add(it.toAlbum())
        }

        return list
    }

    fun toAlbumEntity(): AlbumEntity {
        return AlbumEntity(
            id = id,
            title = title,
            userId = userId
        )
    }
}