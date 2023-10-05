package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.photo_screen.photo_details_screen.photo_zoom_screen.PhotoZoomFragment


@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.maBottomBar?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnbItemPhoto -> replaceFragment(R.id.go_to_photoFragment)
                R.id.bnbItemCollection -> replaceFragment(R.id.go_to_collectionFragment)
                R.id.bnbItemProfile -> replaceFragment(R.id.authFragment)
                else -> false
            }
        }
    }

    override fun onResume() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                when (f) {
                    is PhotoZoomFragment ->
                        goneNavBar()
                }
                super.onFragmentStarted(fm, f)
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                super.onFragmentStopped(fm, f)
                when (f) {
                    is PhotoZoomFragment ->
                        visibleNavBar()
                }
            }
        }, true)
        super.onResume()
    }


    private fun replaceFragment(actionId: Int): Boolean {
        findNavController(R.id.maFragmentContainer).navigate(
            actionId,
            null,
            NavOptions.Builder()
                .setPopUpTo(null, true)
                .setLaunchSingleTop(true)
                .build()
        )
        return true
    }

    fun goneNavBar() {
        binding?.maBottomBar?.visibility = View.GONE

    }

    fun visibleNavBar() {
        binding?.maBottomBar?.visibility = View.VISIBLE
//        binding?.maBottomBar?.menu.apply {
//            R.menu.bottom_nav_bar_menu
//        }
    }

}







