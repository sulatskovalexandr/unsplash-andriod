package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.main.screens.general_screen.GeneralFragment
import com.example.myapplication.main.screens.photo_details_screen.PhotoDetailsFragment

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
    Функция добавления GeneralFragment в fragment_container
     **/
    private fun openGeneralScreen() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, GeneralFragment())
            .setReorderingAllowed(true)
            .commit()
    }

    fun openPhotoDetailsScreen(photoId: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PhotoDetailsFragment())
            .setReorderingAllowed(true)
            .commit()
    }


}