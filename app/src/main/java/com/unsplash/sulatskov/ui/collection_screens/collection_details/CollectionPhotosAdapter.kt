package com.unsplash.sulatskov.ui.collection_screens.collection_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.databinding.ItemPhotoBinding
import com.unsplash.sulatskov.domain.model.CollectionPhotos

class CollectionPhotosAdapter(private val clickListener: CollectionPhotoClickListener) :
    RecyclerView.Adapter<CollectionPhotosHolder>() {

    private val listCollectionPhotos = mutableListOf<CollectionPhotos>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionPhotosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return CollectionPhotosHolder(view, clickListener)
    }

    override fun getItemCount(): Int = listCollectionPhotos.size

    override fun onBindViewHolder(holder: CollectionPhotosHolder, position: Int) {
        holder.bindCollectionPhotos(listCollectionPhotos[position])
    }

    fun addCollectionPhoto(collectionPhotos: List<CollectionPhotos>) {
        listCollectionPhotos.addAll(collectionPhotos)
        notifyItemInserted(listCollectionPhotos.size - 1)
    }
}

class CollectionPhotosHolder(
    itemView: View,
    private val clickListener: CollectionPhotoClickListener
) :
    RecyclerView.ViewHolder(itemView) {

    val binding = ItemPhotoBinding.bind(itemView)

    fun bindCollectionPhotos(collectionPhotos: CollectionPhotos) = with(binding) {

        Glide.with(itemView)
            .load(collectionPhotos.user.profileImage.medium)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(collectionPhotos.user.profileImage.small)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fpItemProfileImage)

        Glide.with(itemView)
            .load(collectionPhotos.urls.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(collectionPhotos.urls.small)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fpItemImage)

        ZoomHelper.addZoomableView(binding.fpItemImage)

        fpItemName.text = collectionPhotos.user.userName
        fpItemLocation.visibility = View.GONE

        binding.fpItemProfileImage.setOnClickListener {
            clickListener.onProfileImageClick(
                photoProfile = collectionPhotos.user.profileImage.medium.toString(),
                userName = collectionPhotos.user.userName.toString()
            )
        }
        binding.fpItemImage.setOnClickListener {
            clickListener.onPhotoClick(
                photoId = collectionPhotos.id,
                photoUrl = collectionPhotos.urls.regular.toString(),
                photoProfile = collectionPhotos.user.profileImage.medium.toString(),
                userName = collectionPhotos.user.userName.toString()
            )
        }
    }
}

interface CollectionPhotoClickListener {

    fun onPhotoClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)
    fun onProfileImageClick(photoProfile: String, userName: String)
}