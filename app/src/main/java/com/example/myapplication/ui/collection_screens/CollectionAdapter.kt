package com.example.myapplication.ui.collection_screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCollectionBinding
import com.example.myapplication.domain.model.Collection

class CollectionAdapter() : RecyclerView.Adapter<CollectionHolder>() {

    private val listCollection = mutableListOf<Collection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionHolder(view)
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

class CollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = ItemCollectionBinding.bind(itemView)

    fun bindCollection(collection: Collection) = with(binding) {

        Glide
            .with(itemView)
            .load(collection.coverPhoto.url.regular)
            .into(fcItemImage)

        Glide.with(itemView)
            .load(collection.user.profileImage.medium)
            .into(fcItemProfileImage)

        binding.fcItemUserName.text = collection.user.name
        binding.fcItemCollectionTitle.text = collection.title
        binding.fcQuantityPhoto.text = "${collection.totalPhoto} Фотографии"


    }

}
