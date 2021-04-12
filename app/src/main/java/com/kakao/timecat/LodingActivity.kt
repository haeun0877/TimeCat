package com.kakao.timecat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LodingActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long =300 //1sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)

        Handler().postDelayed({
            var intent = Intent(this,  MainActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}