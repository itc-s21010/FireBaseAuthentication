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

        imageView = findViewById(R.id.imageView)

        imageView.setOnClickListener {
            openUrl("http://54.199.142.48/wordpress/")
        }
    }

    private fun openUrl(link:String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}