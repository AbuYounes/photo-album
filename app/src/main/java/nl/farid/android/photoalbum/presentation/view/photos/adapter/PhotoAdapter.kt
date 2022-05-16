package nl.farid.android.photoalbum.presentation.view.photos.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import nl.farid.android.photoalbum.databinding.RowPhotoBinding
import nl.farid.android.photoalbum.model.app_model.Photo
import com.bumptech.glide.load.model.LazyHeaders

import com.bumptech.glide.load.model.GlideUrl

class PhotoAdapter(private val requestManager: RequestManager): RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var photos: List<Photo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = RowPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = photos[position]

        val url = GlideUrl(
            item.thumbnailUrl, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build()
        )

        with(holder){
            requestManager
                .load(url)
                .centerInside()
                .into(object : CustomTarget<Drawable>(){
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.photoAlbumImg.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        binding.photoAlbumImg.setImageDrawable(placeholder)
                    }
                })
            binding.photoAlbumTitle.text = item.title
        }
    }

    override fun getItemCount(): Int = photos.size

    fun setData(list: List<Photo>) {
        val diffUtil = PhotoDiffUtil(photos, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        photos = list
        diffResults.dispatchUpdatesTo(this)
    }

    inner class PhotoViewHolder(val binding: RowPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)
}