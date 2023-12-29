package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.constants.Const
import com.example.myapplication.ui.collection_screens.CollectionFragment
import com.example.myapplication.ui.login_screen.LoginFragment
import com.example.myapplication.ui.photo_screen.PhotoFragment

class ActivityPagerAdapter(
    fm: FragmentManager,
    val userName: String
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = NUM_ITEMS

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