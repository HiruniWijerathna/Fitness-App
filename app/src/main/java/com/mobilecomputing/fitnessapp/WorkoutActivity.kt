//package com.mobilecomputing.fitnessapp
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class WorkoutActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_workout)
//
//        // Workout card clicks → WorkoutDetailActivity
//        val workoutItem1 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.workoutItem1)
//        val workoutItem2 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.workoutItem2)
//        val workoutItem3 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.workoutItem3)
//        val cardArms = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardArms)
//        val cardFullBody = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardFullBody)
//        val cardYoga = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardYoga)
//
//        val goToDetail: () -> Unit = {
//            startActivity(Intent(this, WorkoutDetailActivity::class.java))
//        }
//
//        workoutItem1.setOnClickListener { goToDetail() }
//        workoutItem2.setOnClickListener { goToDetail() }
//        workoutItem3.setOnClickListener { goToDetail() }
//        cardArms.setOnClickListener { goToDetail() }
//        cardFullBody.setOnClickListener { goToDetail() }
//        cardYoga.setOnClickListener { goToDetail() }
//
//        setupBottomNav()
//    }
//
//    private fun setupBottomNav() {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        bottomNav.selectedItemId = R.id.nav_workout
//
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_home -> {
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    true
//                }
//                R.id.nav_achievement -> {
//                    startActivity(Intent(this, AchievementActivity::class.java))
//                    true
//                }
//                R.id.nav_workout -> true
//                R.id.nav_profile -> {
//                    startActivity(Intent(this, ProfileActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}