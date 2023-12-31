package com.example.myapplication.ui.user_screen.user_collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemUserCollectionBinding

class UserCollectionAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<UserCollectionHolder>() {


    private val listUsersCollection =
        mutableListOf<com.example.myapplication.domain.model.Collection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCollectionHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_collection, parent, false)
        return UserCollectionHolder(view, listener)
    }

    override fun getItemCount(): Int = listUsersCollection.size

    override fun onBindViewHolder(holder: UserCollectionHolder, position: Int) {
        holder.bindUserPhoto(listUsersCollection[position])

    }

    fun addUsersPhoto(collection: List<com.example.myapplication.domain.model.Collection>) {
        listUsersCollection.addAll(collection)
        notifyItemInserted(listUsersCollection.size - 1)
    }

    fun clear() {
        listUsersCollection.clear()
        notifyDataSetChanged()
    }
}

class UserCollectionHolder(itemView: View, var listener: ClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemUserCollectionBinding.bind(itemView)

    fun bindUserPhoto(userCollection: com.example.myapplication.domain.model.Collection) =
        with(binding) {
            Glide
                .with(itemView)
                .load(userCollection.coverPhoto.url.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(fucItemImage)

            binding.fucCollectionName.text = userCollection.title
            binding.fucQuantityPhoto.text = "${userCollection.totalPhoto} Фотографии"

            binding.fucItemImage.setOnClickListener {

            }

        }
}

interface ClickListener {
    fun onCollectionClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)
}
