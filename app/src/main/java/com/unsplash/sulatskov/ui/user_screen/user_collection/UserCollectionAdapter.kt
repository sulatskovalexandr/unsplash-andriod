package com.unsplash.sulatskov.ui.user_screen.user_collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.databinding.ItemUserCollectionBinding
import com.unsplash.sulatskov.domain.model.CollectionDto

class UserCollectionAdapter(private val clicklistener: UserCollectionClickListener) :
    RecyclerView.Adapter<UserCollectionHolder>() {


    private val listUsersCollectionDto =
        mutableListOf<CollectionDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCollectionHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_collection, parent, false)
        return UserCollectionHolder(view, clicklistener)
    }

    override fun getItemCount(): Int = listUsersCollectionDto.size

    override fun onBindViewHolder(holder: UserCollectionHolder, position: Int) {
        holder.bindUserPhoto(listUsersCollectionDto[position])
    }

    fun addUsersPhoto(collectionDto: List<CollectionDto>) {
        listUsersCollectionDto.addAll(collectionDto)
        notifyItemInserted(listUsersCollectionDto.size - 1)
    }

    fun clear() {
        listUsersCollectionDto.clear()
        notifyDataSetChanged()
    }
}

class UserCollectionHolder(itemView: View, var clicklistener: UserCollectionClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemUserCollectionBinding.bind(itemView)

    fun bindUserPhoto(userCollectionDto: CollectionDto) =
        with(binding) {
            Glide
                .with(itemView)
                .load(userCollectionDto.coverPhoto.url?.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(
                    Glide.with(itemView)
                        .load(userCollectionDto.coverPhoto.url?.regular)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .override(2, 2)
                )
                .error(R.drawable.error_circle_image)
//                .placeholder(itemView.context.getProgressBar())
                .into(fucItemImage)

            binding.fucCollectionName.text = userCollectionDto.title
            binding.fucQuantityPhoto.text = "${userCollectionDto.totalPhoto} Фотографии"

            binding.fucItemImage.setOnClickListener {
                clicklistener.onCollectionClick(
                    collectionId = userCollectionDto.id,
                    title = userCollectionDto.title
                )
            }
        }
}

interface UserCollectionClickListener {
    fun onCollectionClick(collectionId: String, title: String)
}
