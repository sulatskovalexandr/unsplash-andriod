package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.main.screens.general_screen.GeneralFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (supportFragmentManager.backStackEntryCount == 0) {
            openGeneralScreen()
        }
    }

    private fun openGeneralScreen() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, GeneralFragment())
            .setReorderingAllowed(true)
            .commit()
    }


}