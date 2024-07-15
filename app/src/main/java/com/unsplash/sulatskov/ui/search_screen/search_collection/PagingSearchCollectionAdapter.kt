package com.unsplash.sulatskov.ui.search_screen.search_collection

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
import com.unsplash.sulatskov.ui.collection_screens.CollectionClickListener

class PagingSearchCollectionAdapter(private val clickListener: CollectionClickListener) :
    PagingDataAdapter<CollectionDto, CollectionHolder>(SearchCollectionDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        holder.bindCollection(getItem(position))
    }

//    fun setCollection(collectionDto: List<CollectionDto>) {
//        listSearchCollection.clear()
//        listSearchCollection.addAll(collectionDto)
//        notifyDataSetChanged()
//    }

//    fun clear() {
//        listSearchCollection.clear()
//        notifyDataSetChanged()
//    }
}

class CollectionHolder(itemView: View, private val clickListener: CollectionClickListener) :
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
            .load(collection?.user?.profileImage?.medium)
            .thumbnail(
                Glide.with(itemView)
                    .load(collection?.user?.profileImage?.medium)
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
                collection?.user?.profileImage.toString(),
                collection?.user?.userName.toString()
            )
        }
        binding.fcItemImage.setOnClickListener {
            clickListener.onCollectionClick(collectionId = collection!!.id, title = collection.title)
        }
    }
}
object SearchCollectionDiffItemCallback : DiffUtil.ItemCallback<CollectionDto>() {
    override fun areItemsTheSame(oldItem: CollectionDto, newItem: CollectionDto): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CollectionDto, newItem: CollectionDto): Boolean =
        oldItem.user.profileImage?.medium == newItem.user.profileImage?.medium &&
                oldItem.user.name == newItem.user.name &&
                oldItem.coverPhoto.url?.regular == newItem.coverPhoto.url?.regular &&
                oldItem.title == newItem.title &&
                oldItem.totalPhoto == newItem.totalPhoto
}
