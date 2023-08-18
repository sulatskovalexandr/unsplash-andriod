package com.example.myapplication.photo_details_screen.presentation.photo_details_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemTagsBinding
import com.example.myapplication.general_screen.domain.model.Tag

class PhotoDetailsAdapter() : RecyclerView.Adapter<PhotoDetailsHolder>() {
    private val tagList = mutableListOf<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetailsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tags, parent, false)
        return PhotoDetailsHolder(view)
    }

    override fun getItemCount(): Int = tagList.size

    override fun onBindViewHolder(holder: PhotoDetailsHolder, position: Int) {
        holder.bindTeg(tagList[position])
    }

    fun addTags(tag: List<Tag>) {
        tagList.addAll(tag)
        notifyDataSetChanged()
    }
}

class PhotoDetailsHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemTagsBinding.bind(itemView)

    fun bindTeg(tag: Tag) = with(binding) {
        itemTag.text = tag.title

    }
}

