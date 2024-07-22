package com.unsplash.sulatskov.ui.user_screen.users_photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.common.getProgressBar
import com.unsplash.sulatskov.databinding.ItemUserPhotoBinding
import com.unsplash.sulatskov.domain.model.UserPhoto

class UsersPhotoAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<UserPhotoHolder>() {


    private val listUsersPhoto = mutableListOf<UserPhoto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPhotoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_photo, parent, false)
        return UserPhotoHolder(view, listener)
    }

    override fun getItemCount(): Int = listUsersPhoto.size

    override fun onBindViewHolder(holder: UserPhotoHolder, position: Int) {
        holder.bindUserPhoto(listUsersPhoto[position])

    }

    fun addUsersPhoto(photo: List<UserPhoto>) {
        listUsersPhoto.addAll(photo)
        notifyItemInserted(listUsersPhoto.size - 1)
    }

    fun clear() {
        listUsersPhoto.clear()
        notifyDataSetChanged()
    }
}

class UserPhotoHolder(itemView: View, var listener: ClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ItemUserPhotoBinding.bind(itemView)


    fun bindUserPhoto(userPhoto: UserPhoto?) = with(binding) {
        Glide
            .with(itemView)
            .load(userPhoto?.url?.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(
                Glide.with(itemView)
                    .load(userPhoto?.url?.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(2, 2)
            )
            .error(R.drawable.error_circle_image)
            .into(fppItemImage)

        binding.fppItemImage.setOnClickListener {
            listener.onPhotoClick(
                userPhoto?.id.toString(),
                userPhoto?.url?.regular.toString(),
                userPhoto?.user?.profileImage?.medium.toString(),
                userPhoto?.user?.userName.toString()
            )
        }
    }
}

interface ClickListener {
    fun onPhotoClick(photoId: String, photoUrl: String, photoProfile: String, userName: String)
}