package com.unsplash.sulatskov.ui.search_screen

import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.ui.search_screen.search_collection.SearchCollectionFragment
import com.unsplash.sulatskov.ui.search_screen.search_photo.SearchPhotoFragment
import com.unsplash.sulatskov.ui.search_screen.search_user.SearchUserFragment

class SearchPagerAdapter(val fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm) {
    private val fragmentList: List<Fragment> =
        listOf(
            SearchPhotoFragment(), SearchCollectionFragment(),
        )
    private val fragmentTags = SparseArray<String>()

    private fun getFragment(position: Int) =
        fm.findFragmentByTag(fragmentTags.get(position))

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)
        (fragment as Fragment).tag.let { fragmentTags.put(position, it) }
        return fragment
    }

    fun onQuery(query: String) {
        (getFragment(0) as? SearchPhotoFragment)?.onQuery(query)
        (getFragment(1) as? SearchCollectionFragment)?.onQuery(query)
//        (getFragment(2) as? SearchUserFragment)?.onQuery(query)
    }
}