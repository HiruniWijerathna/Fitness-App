package com.mobilecomputing.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val back = findViewById<TextView>(R.id.backToLogin)

        val loginText = findViewById<TextView>(R.id.loginText)

        // Back to Login
        back.setOnClickListener {
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