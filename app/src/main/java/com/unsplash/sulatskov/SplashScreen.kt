package com.unsplash.sulatskov

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.unsplash.sulatskov.databinding.ActivitySplashBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val uAnim = AnimationUtils.loadAnimation(this, R.anim.u_animation)
        val nAnim = AnimationUtils.loadAnimation(this, R.anim.n_animation)
        val sAnim = AnimationUtils.loadAnimation(this, R.anim.s_animation)
        val pAnim = AnimationUtils.loadAnimation(this, R.anim.p_animation)
        val lAnim = AnimationUtils.loadAnimation(this, R.anim.l_animation)
        val aAnim = AnimationUtils.loadAnimation(this, R.anim.a_animation)
        val sAnim2 = AnimationUtils.loadAnimation(this, R.anim.s2_animation)
        val hAnim = AnimationUtils.loadAnimation(this, R.anim.h_animation)
        val iconAnimation = AnimationUtils.loadAnimation(this, R.anim.icon_aniimation)

        splashBinding.uLetter.startAnimation(uAnim)
        splashBinding.nLetter.startAnimation(nAnim)
        splashBinding.sLetter.startAnimation(sAnim)
        splashBinding.pLetter.startAnimation(pAnim)
        splashBinding.lLetter.startAnimation(lAnim)
        splashBinding.aLetter.startAnimation(aAnim)
        splashBinding.sLetter2.startAnimation(sAnim2)
        splashBinding.hLetter.startAnimation(hAnim)

        splashBinding.root.postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, 1500)
    }
}