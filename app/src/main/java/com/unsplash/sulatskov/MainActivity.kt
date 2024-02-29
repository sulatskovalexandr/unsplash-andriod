package com.unsplash.sulatskov

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.aghajari.zoomhelper.ZoomHelper
import com.unsplash.sulatskov.databinding.ActivityMainBinding
import com.unsplash.sulatskov.ui.login_screen.LoginFragment


//@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        /**
         * Переключение между элементами BottomBar
         */
        binding?.maBottomBar?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnbItemPhoto -> replaceFragment(R.id.go_to_photoFragment)
                R.id.bnbItemCollection -> replaceFragment(R.id.go_to_collectionFragment)
                R.id.bnbItemProfile -> replaceFragment(R.id.authFragment)
                else -> false
            }
        }
    }

//    override fun onResume() {
//        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
//            FragmentManager.FragmentLifecycleCallbacks() {
//
//            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
//                when (f) {
//                    is PhotoZoomFragment ->
//                        goneNavBar()
//                }
//                super.onFragmentStarted(fm, f)
//            }
//
//            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
//                super.onFragmentStopped(fm, f)
//                when (f) {
//                    is PhotoZoomFragment ->
//                        visibleNavBar()
//                }
//            }
//        }, true)
//        super.onResume()
//    }

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
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!, this) || super.dispatchTouchEvent(
            ev
        )
    }

    fun goneNavBar() {
        binding?.maBottomBar?.visibility = View.GONE
    }

    fun visibleNavBar() {
        binding?.maBottomBar?.visibility = View.VISIBLE
    }
}