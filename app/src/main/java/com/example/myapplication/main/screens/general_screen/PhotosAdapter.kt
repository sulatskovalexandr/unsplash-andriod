package com.example.myapplication.main.screens.general_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        itemName.text = photo.name
        itemLocation.text = photo.location
        itemImage.setImageResource(photo.image)
        itemProfileImage.setImageResource(photo.profile_image)
    }
}


