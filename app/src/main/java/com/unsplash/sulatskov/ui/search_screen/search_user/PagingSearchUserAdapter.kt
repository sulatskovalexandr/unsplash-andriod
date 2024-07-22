package com.unsplash.sulatskov.ui.search_screen.search_user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.databinding.ItemCollectionBinding
import com.unsplash.sulatskov.databinding.ItemSearchUserBinding
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.ui.collection_screens.CollectionClickListener

class PagingSearchUserAdapter(private val clickListener: SearchUserClickListener) :
    PagingDataAdapter<User, SearchUserHolder>(SearchUserDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return SearchUserHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: SearchUserHolder, position: Int) {
        holder.bindCollection(getItem(position))
    }
}

class SearchUserHolder(itemView: View, private val clickListener: SearchUserClickListener) :
    RecyclerView.ViewHolder(itemView) {

    val binding = ItemSearchUserBinding.bind(itemView)

    fun bindCollection(user: User?) = with(binding) {

        Glide
            .with(itemView)
            .load(user?.profileImage?.medium)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(user?.profileImage?.medium)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
//            .placeholder(itemView.context.getProgressBar())
            .into(suItemProfileImage)

        binding.suItemName.text = user?.name
        binding.suItemUserName.text = user?.userName

        binding.suClContainer.setOnClickListener {
            clickListener.onCardClick(
                user?.profileImage.toString(),
                user?.userName.toString()
            )
        }
    }
}

object SearchUserDiffItemCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.profileImage?.medium == newItem.profileImage?.medium &&
                oldItem.name == newItem.name &&
                oldItem.userName == newItem.userName
}

interface SearchUserClickListener {
    fun onCardClick(photoProfile: String, userName: String)
    fun onPhotoClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)

}