package com.mobilecomputing.fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WorkoutDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)

        val btnBack = findViewById<android.widget.TextView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val btnStartChallenge = findViewById<android.widget.Button>(R.id.btnStartChallenge)
        btnStartChallenge.setOnClickListener {
            startActivity(Intent(this, StartWorkoutActivity::class.java))
        }

        // Day item clicks
        val day1 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day1Item)
        val day2 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day2Item)
        val day3 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day3Item)

        val goToStart: () -> Unit = {
            startActivity(Intent(this, StartWorkoutActivity::class.java))
        }
        day1.setOnClickListener { goToStart() }
        day2.setOnClickListener { goToStart() }
        day3.setOnClickListener { goToStart() }
    }
}
