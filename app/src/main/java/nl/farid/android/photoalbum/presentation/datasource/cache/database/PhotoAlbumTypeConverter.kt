package nl.farid.android.photoalbum.presentation.datasource.cache.database

import com.squareup.moshi.Moshi

object PhotoAlbumTypeConverter {
    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi){
        this.moshi = moshi
    }
}