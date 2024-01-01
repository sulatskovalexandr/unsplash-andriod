package com.unsplash.sulatskov

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.ui.collection_screens.CollectionFragment
import com.unsplash.sulatskov.ui.login_screen.LoginFragment
import com.unsplash.sulatskov.ui.photo_screen.PhotoFragment

class ActivityPagerAdapter(
    fm: FragmentManager,
    val userName: String
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = com.unsplash.sulatskov.ActivityPagerAdapter.Companion.NUM_ITEMS

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.USER_NAME_KEY, userName)
                }
            }
            1 -> CollectionFragment()
            else  ->LoginFragment()
        }
    }

    companion object {
        const val NUM_ITEMS = 3
    }
}