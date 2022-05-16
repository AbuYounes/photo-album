package nl.farid.android.photoalbum.presentation.view.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.model.app_model.Album
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
@Inject constructor(
    private val iDeleteAlbum: IDeleteAlbum,
    private val iGetAllAlbumsFromCache: IGetAllAlbumsFromCache
): ViewModel(){

    val uiState: StateFlow<FavoritesState> = MutableStateFlow(FavoritesState())

    private fun setState(block: FavoritesState.() -> FavoritesState) {
        (uiState as MutableStateFlow).value = block(uiState.value)
    }

    fun getAlbumsFromCache(){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iGetAllAlbumsFromCache.getAllAlbumsFromCache()){
                is GetAllAlbumsFromCacheResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                is GetAllAlbumsFromCacheResult.Success -> {
                    setState { copy(isLoading = false, albums = result.list) }
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

data class FavoritesState(
    val isLoading: Boolean = false,
    val albums: Flow<List<Album>> = emptyFlow(),
    val error: Throwable? = null
)