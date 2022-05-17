package nl.farid.android.photoalbum.presentation.view.albums

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import nl.farid.android.photoalbum.R
import nl.farid.android.photoalbum.databinding.FragmentAlbumBinding
import nl.farid.android.photoalbum.presentation.view.albums.adapter.PhotoAlbumAdapter
import nl.farid.android.photoalbum.util.launchAndRepeatWithViewLifecycle

const val ID: String = "id"

@AndroidEntryPoint
class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val albumViewModel: AlbumViewModel by viewModels()

    private lateinit var photoAlbumAdapter: PhotoAlbumAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAlbumBinding.bind(view)

        setupRecyclerView(binding)
        initViews()
        subscribeObservers(binding)

        albumViewModel.getAlbums()
    }

    private fun setupRecyclerView(binding: FragmentAlbumBinding) {
        photoAlbumAdapter = PhotoAlbumAdapter(requireActivity())
        binding.photoAlbumRecyclerview.apply {
            adapter = photoAlbumAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.photoAlbumRecyclerview.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    private fun initViews() {
        photoAlbumAdapter.setOnItemClickListener { album ->
            albumViewModel.setSelectedAlbum(album)
            val bundle = bundleOf(ID to album.id)
            findNavController().navigate(R.id.action_albumFragment_to_photoFragment, bundle)
        }
    }

    private fun subscribeObservers(binding: FragmentAlbumBinding) {
        launchAndRepeatWithViewLifecycle {
            albumViewModel.uiState.collectLatest { state ->
                if(state.isLoading){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }

                photoAlbumAdapter.setData(state.albums)
            }
        }

        launchAndRepeatWithViewLifecycle {
            albumViewModel.effect.collect {
                Toast.makeText(requireContext(), it.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}