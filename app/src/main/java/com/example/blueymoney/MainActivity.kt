package com.example.blueymoney

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val login = findViewById<Button>(R.id.loginBTN)
        val usuario = findViewById<EditText>(R.id.email_tv)


        login.setOnClickListener {
            val cleanUser = usuario.text.toString().trim()
            if(cleanUser == "Celeste"){
                startActivity(Intent(this,GastosActivity::class.java))
            }else{
                Toast.makeText(this, "No eres mi novia", Toast.LENGTH_SHORT).show()
            }
        }


    }
}