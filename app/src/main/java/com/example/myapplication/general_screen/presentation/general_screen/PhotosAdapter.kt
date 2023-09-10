package com.example.myapplication.general_screen.presentation.general_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.common.getProgressBar
import com.example.myapplication.databinding.ItemPhotosBinding
import com.example.myapplication.general_screen.domain.model.Photo

class PhotosAdapter(val clickListener: PhotoListClickListener) :
    RecyclerView.Adapter<PhotoHolder>() {


    private val listPhotos = mutableListOf<Photo>()

    /**
     * Создание холдера (представление элемента списка в виде макета item_photos,
     * но еще без данных)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.myapplication.R.layout.item_photos, parent, false)
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
        notifyDataSetChanged()
    }

    fun clear() {
        listPhotos.clear()
        notifyDataSetChanged()
    }

}

class PhotoHolder(
    var clickListener: PhotoListClickListener,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemPhotosBinding.bind(itemView)

    /**
     * Передачи данных в разметку
     */
    fun bindPhoto(photo: Photo) = with(binding) {

        Glide.with(itemView)
            .load(photo.profileImage)
            .into(itemProfileImage)

        Glide.with(itemView)
            .load(photo.urls)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(itemView.context.getProgressBar())
            .into(itemImage)


        itemName.text = photo.userName
//        if (photo.user?.location != null) {
//            itemLocation.text = photo.user.location
//        } else
        itemLocation.visibility = View.GONE
        binding.itemImage.setOnClickListener {
            clickListener.onPhotoClick(photo.id, photo.urls)
        }
    }
}

interface PhotoListClickListener {
    fun onPhotoClick(photoId: String, photoUrl: String)
}


