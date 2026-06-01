package com.mobilecomputing.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val back = findViewById<TextView>(R.id.backToLogin)
        val loginText = findViewById<TextView>(R.id.loginText)
        val signUpButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.signUpButton)

        // Back to Login
        back.setOnClickListener {
            finish()
        }

        signUpButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.registerEmail).text.toString().trim()
            val pass = findViewById<EditText>(R.id.registerPassword).text.toString()
            val conf = findViewById<EditText>(R.id.confirmPassword).text.toString()

            if (email.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass != conf) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("fitness_app_prefs", MODE_PRIVATE)
            prefs.edit()
                .putString("saved_email", email)
                .putString("saved_password", pass)
                .apply()
                
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Already have an account? Login
        loginText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}