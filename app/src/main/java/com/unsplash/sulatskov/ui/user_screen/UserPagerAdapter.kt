package com.unsplash.sulatskov.ui.user_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.ui.user_screen.user_collection.UserCollectionFragment
import com.unsplash.sulatskov.ui.user_screen.users_photo.UserPhotoFragment

class UserPagerAdapter(
    fm: FragmentManager,
    private val userName: String, val context: Context
) : FragmentStatePagerAdapter(fm) {

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

    override fun getPageTitle(position: Int): CharSequence {
        val title = if (position == 0) {
            context.getString(R.string.pf_text_photo)
        } else
            context.getString(R.string.pf_text_collection)
        return title
    }

    companion object {
        const val NUM_ITEMS = 2
    }
}