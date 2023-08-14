package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.constants.Const
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.general_screen.presentation.general_screen.GeneralFragment
import com.example.myapplication.photo_details_screen.presentation.photo_details_screen.PhotoDetailsFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        /**
        Условие, при котором открывается GeneralFragment
         **/
        if (supportFragmentManager.backStackEntryCount == 0) {
            openGeneralScreen()
        }
    }

    /**
    Добавления GeneralFragment в fragment_container
     **/
    private fun openGeneralScreen() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, GeneralFragment())
            .commit()
    }

    fun openPhotoDetailsScreen(photoId: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PhotoDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.PHOTO_ID_KEY, photoId)
                }
            })
            .addToBackStack(null)
            .commit()
    }
}