package com.example.myapplication.ui.search_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.ui.search_screen.search_collection.SearchCollectionFragment
import com.example.myapplication.ui.search_screen.search_photo.SearchPhotoFragment
import com.example.myapplication.ui.search_screen.search_user.SearchUserFragment

class SearchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = NUM_ITEM

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotoFragment()
            1 -> SearchCollectionFragment()
            else -> SearchUserFragment()
        }
    }


    companion object {
        private const val NUM_ITEM = 3
    }
}