package com.unsplash.sulatskov.ui.collection_screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.databinding.ItemCollectionBinding
import com.unsplash.sulatskov.domain.model.Collection

class CollectionAdapter(val clickListener: CollectionClickListener) :
    RecyclerView.Adapter<CollectionHolder>() {

    private val listCollectionDto = mutableListOf<Collection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionHolder(view, clickListener)
    }

    override fun getItemCount(): Int = listCollectionDto.size

    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        holder.bindCollection(listCollectionDto[position])
    }

    fun addCollection(collectionDtos: List<Collection>) {
        listCollectionDto.addAll(collectionDtos)
        notifyItemInserted(listCollectionDto.size - 1)
    }

    fun clear() {
        listCollectionDto.clear()
        notifyDataSetChanged()
    }
}

class CollectionHolder(itemView: View, val clickListener: CollectionClickListener) :
    RecyclerView.ViewHolder(itemView) {

    val binding = ItemCollectionBinding.bind(itemView)

    fun bindCollection(collection: Collection) = with(binding) {

        Glide
            .with(itemView)
            .load(collection.urls)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(collection.urls)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
//            .placeholder(itemView.context.getProgressBar())
            .into(fcItemImage)

        Glide.with(itemView)
            .load(collection.profileImage)
            .thumbnail(
                Glide.with(itemView)
                    .load(collection.profileImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fcItemProfileImage)

        binding.fcItemUserName.text = collection.userName
        binding.fcItemCollectionTitle.text = collection.title
        binding.fcQuantityPhoto.text = "${collection.totalPhoto} Фотографии"

        binding.fcItemUser.setOnClickListener {
            clickListener.onProfileImageClick(
                collection.profileImage,
                collection.userName
            )
        }
        binding.fcItemImage.setOnClickListener {
            clickListener.onCollectionClick(collectionId = collection.id , title = collection.title)
        }
    }
}

interface CollectionClickListener {
    fun onCollectionClick(collectionId: String, title: String)
    fun onProfileImageClick(photoProfile: String, userName: String)
}
