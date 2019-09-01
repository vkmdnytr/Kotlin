package com.example.movie.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.movie.R
import com.example.movie.ui.MainActivity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val myAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splashscreen)
        logoImageView.startAnimation(myAnim)
        Handler().postDelayed({
            val intOcan = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intOcan)
        }, 5000)

    }
}
