package com.example.myapplication.ui.user_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.constants.Const
import com.example.myapplication.ui.user_screen.user_collection.UserCollectionFragment
import com.example.myapplication.ui.user_screen.users_photo.UserPhotoFragment

class UserPagerAdapter(
    fm: FragmentManager,
    private val userName: String,
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = NUM_ITEMS

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UserPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.USER_NAME_KEY, userName)
                }
            }
            else -> UserCollectionFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.USER_NAME_KEY, userName)
                }
            }

        }
    }

    companion object {
        const val NUM_ITEMS = 2
    }
}