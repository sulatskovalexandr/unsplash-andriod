package com.unsplash.sulatskov.ui.photo_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.common.getProgressBar
import com.unsplash.sulatskov.databinding.ItemPhotoBinding
import com.unsplash.sulatskov.domain.model.Photo

class PhotoAdapter(private val clickListener: PhotoClickListener) :
    RecyclerView.Adapter<PhotoHolder>() {

    private val listPhotos = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotoHolder(clickListener, view)
    }

    override fun getItemCount(): Int = listPhotos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bindPhoto(listPhotos[position])
    }

    fun addPhoto(photo: List<Photo>) {
        val i = listPhotos.size - 1
        listPhotos.addAll(photo)
        notifyItemRangeInserted(i, photo.size)
    }

    fun clear() {
        listPhotos.clear()
        notifyDataSetChanged()
    }

}

class PhotoHolder(
    private val clickListener: PhotoClickListener,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemPhotoBinding.bind(itemView)

    fun bindPhoto(
        photo: Photo
    ) = with(binding) {

        Glide.with(itemView)
            .load(photo.profileImage)
            .thumbnail(
                Glide.with(itemView)
                    .load(photo.profileImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fpItemProfileImage)

        Glide.with(itemView)
            .load(photo.urls)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(photo.urls)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .placeholder(itemView.context.getProgressBar())
            .into(fpItemImage)

        ZoomHelper.addZoomableView(binding.fpItemImage)

        fpItemName.text = photo.userName

//        if (photo.user?.location != null) {
//            itemLocation.text = photo.user.location
//        } else
        fpItemLocation.visibility = View.GONE
        binding.fpItemImage.setOnClickListener {
            clickListener.onPhotoClick(photo.id, photo.urls, photo.profileImage, photo.userName)
        }
        binding.fpItemUser.setOnClickListener {
            clickListener.onProfileImageClick(photo.profileImage, photo.userName)
        }
    }
}

interface PhotoClickListener {
    fun onPhotoClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)

    fun onProfileImageClick(photoProfile: String, userName: String)
}


