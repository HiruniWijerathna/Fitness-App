//package com.mobilecomputing.fitnessapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//
//class WorkoutDetailActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_workout_detail)
//
//        val imageResId = intent.getIntExtra("IMAGE_RES_ID", R.drawable.background2)
//        val title = intent.getStringExtra("TITLE") ?: "Workout"
//        val description = intent.getStringExtra("DESCRIPTION") ?: "Type: Mixed"
//        val videoResId = intent.getIntExtra("VIDEO_RES_ID", R.raw.pw)
//
//        val subImages = intent.getIntArrayExtra("SUB_IMAGES")
//        val subTitles = intent.getStringArrayExtra("SUB_TITLES")
//
//        val heroImage = findViewById<ImageView>(R.id.heroImage)
//        val workoutTitle = findViewById<TextView>(R.id.workoutTitle)
//        val workoutSubtitle = findViewById<TextView>(R.id.workoutSubtitle)
//
//        heroImage.setImageResource(imageResId)
//        workoutTitle.text = title
//        workoutSubtitle.text = description
//
//        val btnBack = findViewById<TextView>(R.id.btnBack)
//        btnBack.setOnClickListener { finish() }
//
//        val goToStart: () -> Unit = {
//            val startIntent = Intent(this, StartWorkoutActivity::class.java)
//            startIntent.putExtra("VIDEO_RES_ID", videoResId)
//
//            if (subImages != null && subTitles != null && subImages.isNotEmpty()) {
//                startIntent.putExtra("NEXT_IMAGE_RES_ID", subImages[0])
//                startIntent.putExtra("NEXT_TITLE", subTitles[0])
//                startIntent.putExtra("NEXT_DESC", "Type: Related Workout")
//            }
//
//            startActivity(startIntent)
//        }
//
//        val btnStartChallenge = findViewById<Button>(R.id.btnStartChallenge)
//        btnStartChallenge.setOnClickListener { goToStart() }
//
//        // Day item views
//        val day1Item = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day1Item)
//        val day2Item = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day2Item)
//        val day3Item = findViewById<com.google.android.material.card.MaterialCardView>(R.id.day3Item)
//
//        day1Item.setOnClickListener { goToStart() }
//        day2Item.setOnClickListener { goToStart() }
//        day3Item.setOnClickListener { goToStart() }
//
//        // Hide all by default, then show and populate if data exists
//        day1Item.visibility = View.GONE
//        day2Item.visibility = View.GONE
//        day3Item.visibility = View.GONE
//
//        if (subImages != null && subTitles != null) {
//            if (subImages.isNotEmpty()) {
//                day1Item.visibility = View.VISIBLE
//                findViewById<ImageView>(R.id.day1Image).setImageResource(subImages[0])
//                findViewById<TextView>(R.id.day1Title).text = subTitles[0]
//            }
//            if (subImages.size > 1) {
//                day2Item.visibility = View.VISIBLE
//                findViewById<ImageView>(R.id.day2Image).setImageResource(subImages[1])
//                findViewById<TextView>(R.id.day2Title).text = subTitles[1]
//            }
//            if (subImages.size > 2) {
//                day3Item.visibility = View.VISIBLE
//                findViewById<ImageView>(R.id.day3Image).setImageResource(subImages[2])
//                findViewById<TextView>(R.id.day3Title).text = subTitles[2]
//            }
//        }
//
//        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
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
