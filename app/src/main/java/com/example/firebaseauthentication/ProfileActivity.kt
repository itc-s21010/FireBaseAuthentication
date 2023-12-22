package com.example.firebaseauthentication

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var textFullname: TextView
    lateinit var textEmail: TextView
    lateinit var btnLogout: Button

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("status", "onCreate")
        setContentView(R.layout.activity_profile)
        textFullname = findViewById(R.id.full_name)
        textEmail = findViewById(R.id.email)
        btnLogout = findViewById(R.id.btn_logout)

        val button: Button = findViewById(R.id.selectbutton)
        button.setOnClickListener(this)

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null){
            textFullname.text = firebaseUser.displayName
            textEmail.text = firebaseUser.email
        }else{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val mapOptionButton: ImageButton = findViewById(R.id.mapOptionMenu)
        val popupMenu = PopupMenu(this, mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }
        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun changeMap(itemId: Int) {
        when(itemId)
        {
            R.id.translation -> {
                val intent = Intent(
                    this@ProfileActivity, TranslateActivity::class.java
                )
                startActivity(intent)
            }
            R.id.emergency -> {
                val intent = Intent(
                    this@ProfileActivity, EmergencyActivity::class.java
                )
                startActivity(intent)
            }
            R.id.passreset -> {
                val intent = Intent(
                    this@ProfileActivity, ResetPasswordActivity::class.java
                )
                startActivity(intent)
            }
            R.id.mapssdk -> {
                val intent = Intent(
                    this@ProfileActivity, MapsActivity::class.java
                )
                startActivity(intent)
            }
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.selectbutton -> {
                selectPhoto()
            }
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("registerForActivityResult(result)", result.toString())

        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        } else {
            try {
                result.data?.data?.also { uri : Uri ->
                    val inputStream = contentResolver?.openInputStream(uri)
                    val image = BitmapFactory.decodeStream(inputStream)
                    val imageView: ImageView = findViewById(R.id.imageView)
                    imageView.setImageBitmap(image)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        launcher.launch(intent)
    }
}