package nl.farid.android.photoalbum.presentation.view.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import nl.farid.android.photoalbum.R
import nl.farid.android.photoalbum.databinding.FragmentPhotoBinding
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.presentation.view.albums.ID
import nl.farid.android.photoalbum.presentation.view.photos.adapter.PhotoAdapter
import nl.farid.android.photoalbum.util.launchAndRepeatWithViewLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment: Fragment(R.layout.fragment_photo) {
    private val photoViewModel: PhotoViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter
    @Inject
    lateinit var requestManager: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPhotoBinding.bind(view)

        photoViewModel.getSelectedAlbum()
        photoViewModel.getAlbumsFromCache()
        setupRecyclerView(binding)
        initViews(binding)
        subscribeObservers(binding)
        arguments?.let { photoViewModel.getPhotosOfAlbum(it.getInt(ID)) }
    }

    private fun setupRecyclerView(binding: FragmentPhotoBinding) {
        photoAdapter = PhotoAdapter(requestManager)
        binding.photoRecyclerview.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.photoRecyclerview.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    private fun initViews(binding: FragmentPhotoBinding) {
        binding.favoritesFab.setOnClickListener {
            if(!photoViewModel.uiState.value.isMarkedAsFavorite){
                photoViewModel.uiState.value.album?.let { it1 -> photoViewModel.setFavoriteAlbum(it1) }
            } else {
                photoViewModel.uiState.value.album?.let { it1 -> photoViewModel.deleteAlbum(it1.id) }
            }
        }
    }


    private fun subscribeObservers(binding: FragmentPhotoBinding) {
        launchAndRepeatWithViewLifecycle {
            photoViewModel.uiState.collect { state ->
                if(state.isLoading){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }

                if(state.isMarkedAsFavorite){
                    binding.favoritesFab.setImageResource(R.drawable.favorite)
                } else {
                    binding.favoritesFab.setImageResource(R.drawable.not_favorite_border)
                }
                photoAdapter.setData(state.photos)
            }
        }
    }
}