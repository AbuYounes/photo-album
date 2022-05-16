package nl.farid.android.photoalbum.presentation.view.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.model.app_model.Album
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject constructor(
    private val iGetAlbums: IGetAlbums,
    private val iMarkAlbumAsFavorite: IMarkAlbumAsFavorite,
    private val iDeleteAlbum: IDeleteAlbum
): ViewModel(){

    val uiState: StateFlow<AlbumState> = MutableStateFlow(AlbumState())

    private fun setState(block: AlbumState.() -> AlbumState) {
        (uiState as MutableStateFlow).value = block(uiState.value)
    }

    fun getAlbums(){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iGetAlbums.getAlbums()){
                is GetAlbumResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                is GetAlbumResult.Success -> {
                    setState { copy(isLoading = false, albums = result.list) }
                }
            }
        }
    }

    fun setFavoriteAlbum(album: Album){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iMarkAlbumAsFavorite.markAsFavorite(album)){
                is MarkAlbumAsFavoriteResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                MarkAlbumAsFavoriteResult.Success -> {
                    setState { copy(isLoading = false ) }
                }
            }
        }
    }

    fun deleteAlbum(albumId: Int){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iDeleteAlbum.deleteAlbum(albumId)){
                is DeleteAlbumResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                DeleteAlbumResult.Success -> {
                    setState { copy(isLoading = false) }
                }
            }
        }
    }
}

data class AlbumState(
    val isLoading: Boolean = false,
    val isMarkedAsFavorite: Boolean = false,
    val albums: Flow<List<Album>> = emptyFlow(),
    val error: Throwable? = null
)