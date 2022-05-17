package nl.farid.android.photoalbum.presentation.view.albums.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nl.farid.android.photoalbum.R
import nl.farid.android.photoalbum.databinding.RowAlbumBinding
import nl.farid.android.photoalbum.model.app_model.Album

class PhotoAlbumAdapter(private val context: Context): RecyclerView.Adapter<PhotoAlbumAdapter.PhotoAlbumViewHolder>() {
    private var photoAlbums: List<Album> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAlbumViewHolder {
        val binding = RowAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return PhotoAlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoAlbumViewHolder, position: Int) {
        val item = photoAlbums[position]
        with(holder){
            binding.title.text = item.title
        }

        holder.binding.title.setOnClickListener {
            notifyItemChanged(position)
            onClickListener?.let { it(item) }
        }

    }

    override fun getItemCount(): Int = photoAlbums.size

    fun setData(list: List<Album>) {
        val diffUtil = PhotoAlbumDiffUtil(photoAlbums, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        photoAlbums = list
        diffResults.dispatchUpdatesTo(this)
    }

    private var onClickListener: ((Album) -> Unit)? = null

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        onClickListener = listener
    }



    inner class PhotoAlbumViewHolder(val binding: RowAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)
}