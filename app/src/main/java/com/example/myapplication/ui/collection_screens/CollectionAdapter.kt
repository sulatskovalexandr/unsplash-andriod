package com.example.myapplication.ui.collection_screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCollectionBinding
import com.example.myapplication.domain.model.Collection

class CollectionAdapter(val clickListener: CollectionClickListener) :
    RecyclerView.Adapter<CollectionHolder>() {

    private val listCollection = mutableListOf<Collection>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionHolder(view, clickListener)
    }

    override fun getItemCount(): Int = listCollection.size

    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        holder.bindCollection(listCollection[position])
    }

    fun addCollection(collections: List<Collection>) {
        listCollection.addAll(collections)
        notifyItemInserted(listCollection.size - 1)
    }

    fun clear() {
        listCollection.clear()
        notifyDataSetChanged()
    }
}

class CollectionHolder(itemView: View, val clickListener: CollectionClickListener) :
    RecyclerView.ViewHolder(itemView) {

    val binding = ItemCollectionBinding.bind(itemView)

    fun bindCollection(collection: Collection) = with(binding) {

        Glide
            .with(itemView)
            .load(collection.coverPhoto.url?.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(collection.coverPhoto.url?.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .override(2, 2)
            )
            .error(R.drawable.ic_error)
//            .placeholder(itemView.context.getProgressBar())
            .into(fcItemImage)

        Glide.with(itemView)
            .load(collection.user.profileImage.medium)
            .thumbnail(
                Glide.with(itemView)
                    .load(collection.user.profileImage.medium)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .override(2, 2)
            )
            .into(fcItemProfileImage)

        binding.fcItemUserName.text = collection.user.name
        binding.fcItemCollectionTitle.text = collection.title
        binding.fcQuantityPhoto.text = "${collection.totalPhoto} Фотографии"

        binding.fcItemProfileImage.setOnClickListener {
            clickListener.onProfileImageClick(
                photoProfile = collection.user.profileImage.medium.toString(),
                collection.user.userName.toString()
            )
        }
    }
}

interface CollectionClickListener {
//    fun onCollectionClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)

    fun onProfileImageClick(photoProfile: String, userName: String)
}
