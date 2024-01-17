package com.unsplash.sulatskov

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.aghajari.zoomhelper.ZoomHelper
import com.unsplash.sulatskov.databinding.ActivityMainBinding
import com.unsplash.sulatskov.ui.login_screen.LoginFragment
import com.unsplash.sulatskov.ui.photo_screen.photo_details_screen.photo_zoom_screen.PhotoZoomFragment


@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {

//     2
//    private val photoFragment = PhotoFragment()
//    private val collectionFragment = CollectionFragment()
//    private val loginFragment = LoginFragment()
//    private val fragmentManager = supportFragmentManager
//    private var activeFragment: Fragment = photoFragment
    //    private val adapter = ActivityPagerAdapter(supportFragmentManager,"")

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.maFragmentContainer) as NavHostFragment
//        val navController = navHostFragment.navController
//        binding?.maBottomBar?.setupWithNavController(navController)

//        if (savedInstanceState == null) {
//            setUpBottomNavigationBarBase()
//        }

//        2
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.maFragmentContainer, photoFragment)
//            add(R.id.maFragmentContainer, collectionFragment).hide(collectionFragment)
//            add(R.id.maFragmentContainer, loginFragment).hide(loginFragment)
//        }.commit()


        /**
         * Переключение между элементами BottomBar
         */
        binding?.maBottomBar?.setOnItemSelectedListener { item ->
            when (item.itemId) {

//                2
//                R.id.bnbItemPhoto -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(photoFragment).commit()
//                    activeFragment = photoFragment
//                    true
//                }
//                R.id.bnbItemCollection ->{
//                    fragmentManager.beginTransaction().hide(activeFragment).show(collectionFragment).commit()
//                    activeFragment = collectionFragment
//                    true
//                }
//                R.id.bnbItemProfile ->{
//                    fragmentManager.beginTransaction().hide(activeFragment).show(loginFragment).commit()
//                    activeFragment = loginFragment
//                    true
//                }
//                else -> false
////
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

    override fun onNewIntent(intent: Intent?) {
        val navFragment = supportFragmentManager.findFragmentById(R.id.maFragmentContainer)
        val fragment = navFragment?.childFragmentManager?.primaryNavigationFragment
        (fragment as? LoginFragment)?.onNewIntent(intent)
        super.onNewIntent(intent)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!,this) || super.dispatchTouchEvent(ev)
    }

    fun goneNavBar() {
        binding?.maBottomBar?.visibility = View.GONE
    }

    fun visibleNavBar() {
        binding?.maBottomBar?.visibility = View.VISIBLE
    }
}