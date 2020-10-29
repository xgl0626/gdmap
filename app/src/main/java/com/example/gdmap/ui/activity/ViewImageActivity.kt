package com.example.gdmap.ui.activity


import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gdmap.R
import kotlinx.android.synthetic.main.activity_view_image.*


class ViewImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        setTheme(R.style.Theme_MaterialComponents)
        val bitmap = intent.getParcelableExtra<Bitmap>("url")
        view_image.setImageBitmap(bitmap)
        view_image.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}