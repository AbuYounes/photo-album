package nl.farid.android.photoalbum.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview
import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.data.cache.implementation.PhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.data.network.abstraction.IPhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.business.data.network.implementation.PhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.repository.implementation.PhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.business.usecase.implementation.*
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao
import nl.farid.android.photoalbum.presentation.datasource.cache.database.AppDatabase
import nl.farid.android.photoalbum.presentation.datasource.network.service.PhotoAlbumService
import nl.farid.android.photoalbum.util.Constants
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoAlbumModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Bindings {

        @FlowPreview
        @Binds
        abstract fun bindPhotoAlbumRepository(photoAlbumRepository: PhotoAlbumRepository): IPhotoAlbumRepository

        @Binds
        abstract fun bindGetAlbumsUseCase(useCase: GetAlbums): IGetAlbums

        @Binds
        abstract fun bindGetPhotosFromAlbumUseCase(useCase: GetPhotosFromAlbum): IGetPhotosFromAlbum

        @Binds
        abstract fun bindGetAllAlbumsFromCacheUseCase(useCase: GetAllAlbumsFromCache): IGetAllAlbumsFromCache


        @Binds
        abstract fun bindGetAlbumsFromCacheUseCase(useCase: GetAlbumsFromCache): IGetAlbumsFromCache

        @Binds
        abstract fun bindMarkAlbumAsFavoriteUseCase(useCase: MarkAlbumAsFavorite): IMarkAlbumAsFavorite

        @Binds
        abstract fun bindDeleteAlbumUseCase(useCase: DeleteAlbum): IDeleteAlbum
    }

    @Singleton
    @Provides
    fun providePhotoAlbumCacheDataSource(photoAlbumDao: PhotoAlbumDao): IPhotoAlbumCacheDataSource =
        PhotoAlbumCacheDataSource(photoAlbumDao)

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): PhotoAlbumDao = appDatabase.getPhotoAlbumDao()

    @Singleton
    @Provides
    fun providePhotoAlbumNetworkDataSource(photoAlbumService: PhotoAlbumService): IPhotoAlbumNetworkDataSource =
        PhotoAlbumNetworkDataSource(photoAlbumService)

    @Singleton
    @Provides
    fun providePhotoAlbumService(
        retrofitBuilder: Retrofit.Builder,
    ): PhotoAlbumService {
        return retrofitBuilder
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PhotoAlbumService::class.java)
    }
}