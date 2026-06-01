package com.mobilecomputing.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val registerText =
            findViewById<TextView>(R.id.registerText)

        registerText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )

        }

        val loginButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEdit).text.toString().trim()
            val pass = findViewById<EditText>(R.id.passwordEdit).text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("fitness_app_prefs", MODE_PRIVATE)
            val savedEmail = prefs.getString("saved_email", "")
            val savedPass = prefs.getString("saved_password", "")

            if (email == savedEmail && pass == savedPass) {
                // Correct credentials
                prefs.edit().putBoolean("is_logged_in", true).apply()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}