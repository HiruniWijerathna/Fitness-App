//package com.mobilecomputing.fitnessapp
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class AchievementActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_achievement)
//
//        setupBottomNav()
//    }
//
//    private fun setupBottomNav() {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        bottomNav.selectedItemId = R.id.nav_achievement
//
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_home -> {
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    true
//                }
//                R.id.nav_achievement -> true
//                R.id.nav_workout -> {
//                    startActivity(Intent(this, WorkoutActivity::class.java))
//                    true
//                }
//                R.id.nav_profile -> {
//                    startActivity(Intent(this, ProfileActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}