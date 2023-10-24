package com.example.firebaseauthentication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class EmergencyActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)

        button.setOnClickListener {
            openUrl("https://www.kifjp.org/disaster/news_eng/497")
        }
        imageView.setOnClickListener {
            openUrl("https://www.navitime.co.jp/category/0502008/47/")
        }
    }

    private fun openUrl(link:String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}