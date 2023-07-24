package com.example.myapplication.main.screens.general_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPhotosBinding


class PhotosAdapter : RecyclerView.Adapter<PhotoHolder>() {


    private val listPhotos = mutableListOf<Photos>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photos, parent, false)
        return PhotoHolder(view)
    }

    override fun getItemCount(): Int = listPhotos.size


    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bindPhoto(listPhotos[position])
    }

    fun addPhoto(photo: List<Photos>) {
        listPhotos.addAll(photo)
        notifyDataSetChanged()
    }
}

class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPhotosBinding.bind(itemView)

    fun bindPhoto(photo: Photos) = with(binding) {

        Glide.with(itemView)
            .load(photo.user?.profileImage?.medium)
            .circleCrop()
            .into(itemProfileImage)

        Glide.with(itemView)
            .load(photo.urls?.regular)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(itemImage)

        itemName.text = photo.user?.userName
        itemLocation.text = photo.user?.location
    }
}


