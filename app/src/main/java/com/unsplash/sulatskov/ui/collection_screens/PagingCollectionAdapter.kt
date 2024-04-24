package com.unsplash.sulatskov.ui.collection_screens

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
import com.unsplash.sulatskov.domain.model.CollectionDto

class PagingCollectionAdapter(private val clickListener: CollectionClickListener) :
    PagingDataAdapter<CollectionDto, PagingCollectionHolder>(CollectionDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingCollectionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return PagingCollectionHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: PagingCollectionHolder, position: Int) {
        holder.bindCollection(getItem(position))
    }
}

class PagingCollectionHolder(itemView: View, private val clickListener: CollectionClickListener) :
    RecyclerView.ViewHolder(itemView) {

    val binding = ItemCollectionBinding.bind(itemView)

    fun bindCollection(collection: CollectionDto?) = with(binding) {

        Glide
            .with(itemView)
            .load(collection?.coverPhoto?.url?.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(collection?.coverPhoto?.url?.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
//            .placeholder(itemView.context.getProgressBar())
            .into(fcItemImage)

        Glide.with(itemView)
            .load(collection?.user?.profileImage?.large)
            .thumbnail(
                Glide.with(itemView)
                    .load(collection?.user?.profileImage?.large)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fcItemProfileImage)

        binding.fcItemUserName.text = collection?.user?.name
        binding.fcItemCollectionTitle.text = collection?.title
        binding.fcQuantityPhoto.text = "${collection?.totalPhoto} Фотографии"

        binding.fcItemUser.setOnClickListener {
            clickListener.onProfileImageClick(
                collection?.user?.profileImage?.large.toString(),
                collection?.user?.userName.toString()
            )
        }
        binding.fcItemImage.setOnClickListener {
            clickListener.onCollectionClick(
                collectionId = collection?.id.toString(),
                title = collection?.title.toString()
            )
        }
    }
}

private object CollectionDiffItemCallback : DiffUtil.ItemCallback<CollectionDto>() {
    override fun areItemsTheSame(oldItem: CollectionDto, newItem: CollectionDto): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: CollectionDto, newItem: CollectionDto): Boolean {
        return oldItem.user.profileImage?.large == newItem.user.profileImage?.large &&
                oldItem.user.userName == newItem.user.userName &&
                oldItem.coverPhoto.url?.regular == newItem.coverPhoto.url?.regular &&
                oldItem.coverPhoto.description == newItem.description &&
                oldItem.title == newItem.title
    }
}


