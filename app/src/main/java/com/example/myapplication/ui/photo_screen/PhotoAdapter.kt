package com.example.myapplication.ui.photo_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.common.getProgressBar
import com.example.myapplication.databinding.ItemPhotoBinding
import com.example.myapplication.domain.model.Photo

class PhotoAdapter(private val clickListener: PhotoClickListener) :
    RecyclerView.Adapter<PhotoHolder>() {

    private val listPhotos = mutableListOf<Photo>()

    /**
     * Создание холдера (представление элемента списка в виде макета item_photos,
     * но еще без данных)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.myapplication.R.layout.item_photo, parent, false)
        return PhotoHolder(clickListener, view)
    }

    /**
     * Количество элементов в listPhotos
     */
    override fun getItemCount(): Int = listPhotos.size

    /**
     * Передача данных в созданный макет
     */
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {

        holder.bindPhoto(listPhotos[position])

    }

    /**
     * Добавление всех объектов в listPhotos
     */
    fun addPhoto(photo: List<Photo>) {
        listPhotos.addAll(photo)
        notifyItemInserted(listPhotos.size - 1)

    }

    fun clear() {
        listPhotos.clear()
        notifyDataSetChanged()
    }

}

class PhotoHolder(
    var clickListener: PhotoClickListener,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemPhotoBinding.bind(itemView)

    /**
     * Передачи данных в разметку
     */
    fun bindPhoto(
        photo: Photo
    ) = with(binding) {

        Glide.with(itemView)
            .load(photo.profileImage)
            .thumbnail(
                Glide.with(itemView)
                    .load(photo.profileImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .override(2, 2)
            )
            .into(fpItemProfileImage)

        Glide.with(itemView)
            .load(photo.urls)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(photo.urls)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .override(2, 2)
            )
            .error(R.drawable.ic_error)
            .placeholder(itemView.context.getProgressBar())
            .into(fpItemImage)

        fpItemName.text = photo.userName

//        if (photo.user?.location != null) {
//            itemLocation.text = photo.user.location
//        } else
        fpItemLocation.visibility = View.GONE
        binding.fpItemImage.setOnClickListener {
            clickListener.onPhotoClick(photo.id, photo.urls, photo.profileImage, photo.userName)
        }
        binding.fpItemProfileImage.setOnClickListener {
            clickListener.onProfileImageClick(photo.profileImage, photo.userName)
        }
        binding.fpItemName.setOnClickListener {
            clickListener.onProfileImageClick(photo.profileImage, photo.userName)
        }
    }
}

interface PhotoClickListener {
    fun onPhotoClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)

    fun onProfileImageClick(photoProfile: String, userName: String)
}


