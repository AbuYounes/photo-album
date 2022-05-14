package nl.farid.android.photoalbum.di


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.data.cache.implementation.PhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.data.network.abstraction.IPhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.business.data.network.implementation.PhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.repository.implementation.PhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetAlbums
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetPhotosFromAlbum
import nl.farid.android.photoalbum.business.usecase.abstraction.IMarkAlbumAsFavorite
import nl.farid.android.photoalbum.business.usecase.implementation.GetAlbums
import nl.farid.android.photoalbum.business.usecase.implementation.GetPhotosFromAlbum
import nl.farid.android.photoalbum.business.usecase.implementation.MarkAlbumAsFavorite
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao
import nl.farid.android.photoalbum.presentation.datasource.cache.database.AppDatabase
import nl.farid.android.photoalbum.presentation.datasource.network.service.PhotoAlbumService
import nl.farid.android.photoalbum.util.Constants
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object PhotoAlbumModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Bindings {

        @Binds
        abstract fun bindGetAlbumsUseCase(useCase: GetAlbums): IGetAlbums

        @Binds
        abstract fun bindGetPhotosFromAlbumUserUseCase(useCase: GetPhotosFromAlbum): IGetPhotosFromAlbum

        @Binds
        abstract fun bindMarkAlbumAsFavoriteUseCase(useCase: MarkAlbumAsFavorite): IMarkAlbumAsFavorite

    }

    @Provides
    fun providePhotoAlbumRepository(
        iPhotoAlbumCacheDataSource: IPhotoAlbumCacheDataSource
    ): IPhotoAlbumRepository {
        return PhotoAlbumRepository(
            iPhotoAlbumCacheDataSource
        )
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