package com.example.blueymoney

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
        val pista = findViewById<TextView>(R.id.pista)


        login.setOnClickListener {
            val cleanUser = usuario.text.toString().trim()
            if(cleanUser == "Jimpark"){
                startActivity(Intent(this,GastosActivity::class.java))
            }else{
                Toast.makeText(this, "Usuario Incorrecto", Toast.LENGTH_SHORT).show()
                pista.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    pista.visibility = View.INVISIBLE
                }, 3000)
            }
        }


    }
}