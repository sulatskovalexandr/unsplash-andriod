package com.unsplash.sulatskov.ui.search_screen.search_photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.common.getProgressBar
import com.unsplash.sulatskov.databinding.ItemPhotoBinding
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import com.unsplash.sulatskov.ui.photo_screen.PhotoClickListener

class PagingSearchPhotoAdapter(private val clickListener: PhotoClickListener) :
    PagingDataAdapter<PhotoDto, PagingSearchPhotoHolder>(SearchPhotoDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingSearchPhotoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PagingSearchPhotoHolder(clickListener, view)
    }

    override fun onBindViewHolder(holder: PagingSearchPhotoHolder, position: Int) {
        holder.bindPhoto(getItem(position))
    }

//    fun clear() {
//        listSearchPhoto.clear()
//        notifyDataSetChanged()
//    }
}

class PagingSearchPhotoHolder(
    private val clickListener: PhotoClickListener,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemPhotoBinding.bind(itemView)

    fun bindPhoto(
        photo: PhotoDto?
    ) = with(binding) {

        Glide.with(itemView)
            .load(photo?.user?.profileImage?.medium)
            .thumbnail(
                Glide.with(itemView)
                    .load(photo?.user?.profileImage?.medium)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fpItemProfileImage)

        Glide.with(itemView)
            .load(photo?.urls?.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(photo?.urls?.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .placeholder(itemView.context.getProgressBar())
            .into(fpItemImage)

        ZoomHelper.addZoomableView(binding.fpItemImage)

        fpItemName.text = photo?.user?.userName

//        if (photo.user?.location != null) {
//            itemLocation.text = photo.user.location
//        } else
        fpItemLocation.visibility = View.GONE

        binding.fpItemImage.setOnClickListener {
            clickListener.onPhotoClick(
                photo!!.id,
                photo.urls?.regular.toString(),
                photo.user.profileImage?.medium.toString(),
                photo.user.userName.toString()
            )
        }

        binding.fpItemUser.setOnClickListener {
            clickListener.onProfileImageClick(
                photo?.user?.profileImage?.medium.toString(),
                photo?.user?.userName.toString()
            )
        }
    }
}

object SearchPhotoDiffItemCallback : DiffUtil.ItemCallback<PhotoDto>() {
    override fun areItemsTheSame(oldItem: PhotoDto, newItem: PhotoDto): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: PhotoDto, newItem: PhotoDto): Boolean {
        return oldItem.user.profileImage?.medium == newItem.user.profileImage?.medium &&
        oldItem.user.userName == newItem.user.userName &&
        oldItem.urls?.regular == newItem.urls?.regular
    }
}



