package com.mobilecomputing.fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class WorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val profileIcon = findViewById<android.widget.ImageView>(R.id.profileIcon)
        val prefs = getSharedPreferences(HomeActivity.PREFS_NAME, MODE_PRIVATE)
        val savedUri = prefs.getString("profile_image_uri", null)
        if (savedUri != null) {
            try {
                profileIcon.setImageURI(android.net.Uri.parse(savedUri))
            } catch (e: Exception) {}
        }

        // Workout card clicks → WorkoutDetailActivity
        val cardPopular1 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardPopular1)
        val cardPopular2 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardPopular2)
        val cardPopular3 = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardPopular3)
        val programFullBody = findViewById<com.google.android.material.card.MaterialCardView>(R.id.programFullBody)
        val programArms = findViewById<com.google.android.material.card.MaterialCardView>(R.id.programArms)
        val programAbs = findViewById<com.google.android.material.card.MaterialCardView>(R.id.programAbs)
        val programLegs = findViewById<com.google.android.material.card.MaterialCardView>(R.id.programLegs)
        val yogaAccomplish = findViewById<com.google.android.material.card.MaterialCardView>(R.id.yogaAccomplish)
        val yogaDancer = findViewById<com.google.android.material.card.MaterialCardView>(R.id.yogaDancer)
        val yogaCrescent = findViewById<com.google.android.material.card.MaterialCardView>(R.id.yogaCrescent)
        val yogaMorning = findViewById<com.google.android.material.card.MaterialCardView>(R.id.yogaMorning)

        val goToDetail = { imageResId: Int, title: String, description: String, videoResId: Int, subImages: IntArray, subTitles: Array<String> ->
            val intent = Intent(this, WorkoutDetailActivity::class.java).apply {
                putExtra("IMAGE_RES_ID", imageResId)
                putExtra("TITLE", title)
                putExtra("DESCRIPTION", description)
                putExtra("VIDEO_RES_ID", videoResId)
                putExtra("SUB_IMAGES", subImages)
                putExtra("SUB_TITLES", subTitles)
            }
            startActivity(intent)
        }

        // Popular Workouts
        cardPopular1.setOnClickListener { goToDetail(R.drawable.pw1, "Home Workouts for Beginners", "Type : Weight Loss, Full Body", R.raw.pw, intArrayOf(R.drawable.pw2, R.drawable.pw3), arrayOf("Effective Home Workout Routines", "Outside Workout for Beginner")) }
        cardPopular2.setOnClickListener { goToDetail(R.drawable.pw2, "Effective Home Workout Routines", "Type : Weight Loss, Full Body", R.raw.pw, intArrayOf(R.drawable.pw1, R.drawable.pw3), arrayOf("Home Workouts for Beginners", "Outside Workout for Beginner")) }
        cardPopular3.setOnClickListener { goToDetail(R.drawable.pw3, "Outside Workout for Beginner", "Type : Weight Loss, Full Body", R.raw.pw, intArrayOf(R.drawable.pw1, R.drawable.pw2), arrayOf("Home Workouts for Beginners", "Effective Home Workout Routines")) }

        // Program Type
        programFullBody.setOnClickListener { goToDetail(R.drawable.pt1, "Full Body", "Type : Weight Loss, Full Body", R.raw.pt, intArrayOf(R.drawable.pt2, R.drawable.pt3, R.drawable.pt4), arrayOf("Arms", "Legs", "ABS")) }
        programArms.setOnClickListener { goToDetail(R.drawable.pt2, "Arms", "Type : Strength, Arms", R.raw.pt, intArrayOf(R.drawable.pt1, R.drawable.pt3, R.drawable.pt4), arrayOf("Full Body", "Legs", "ABS")) }
        programLegs.setOnClickListener { goToDetail(R.drawable.pt3, "Legs", "Type : Strength, Legs", R.raw.pt, intArrayOf(R.drawable.pt1, R.drawable.pt2, R.drawable.pt4), arrayOf("Full Body", "Arms", "ABS")) }
        programAbs.setOnClickListener { goToDetail(R.drawable.pt4, "ABS", "Type : Core, ABS", R.raw.pt, intArrayOf(R.drawable.pt1, R.drawable.pt2, R.drawable.pt3), arrayOf("Full Body", "Arms", "Legs")) }

        // Yoga
        yogaAccomplish.setOnClickListener { goToDetail(R.drawable.y1, "Accomplish", "Type : Yoga", R.raw.y, intArrayOf(R.drawable.y2, R.drawable.y3, R.drawable.y4), arrayOf("Dancer's", "Crescent Lunge", "Morning Yoga")) }
        yogaDancer.setOnClickListener { goToDetail(R.drawable.y2, "Dancer's", "Type : Yoga", R.raw.y, intArrayOf(R.drawable.y1, R.drawable.y3, R.drawable.y4), arrayOf("Accomplish", "Crescent Lunge", "Morning Yoga")) }
        yogaCrescent.setOnClickListener { goToDetail(R.drawable.y3, "Crescent Lunge", "Type : Yoga", R.raw.y, intArrayOf(R.drawable.y1, R.drawable.y2, R.drawable.y4), arrayOf("Accomplish", "Dancer's", "Morning Yoga")) }
        yogaMorning.setOnClickListener { goToDetail(R.drawable.y4, "Morning Yoga", "Type : Yoga", R.raw.y, intArrayOf(R.drawable.y1, R.drawable.y2, R.drawable.y3), arrayOf("Accomplish", "Dancer's", "Crescent Lunge")) }

        val searchWorkout = findViewById<android.widget.EditText>(R.id.searchWorkout)
        val workoutCards = listOf(
            Pair(cardPopular1, "Home Workouts for Beginners"),
            Pair(cardPopular2, "Effective Home Workout Routines"),
            Pair(cardPopular3, "Outside Workout for Beginner"),
            Pair(programFullBody, "Full Body"),
            Pair(programArms, "Arms"),
            Pair(programLegs, "Legs"),
            Pair(programAbs, "ABS"),
            Pair(yogaAccomplish, "Accomplish"),
            Pair(yogaDancer, "Dancer's"),
            Pair(yogaCrescent, "Crescent Lunge"),
            Pair(yogaMorning, "Morning Yoga")
        )

        searchWorkout.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s?.toString()?.lowercase(java.util.Locale.getDefault()) ?: ""
                for ((card, title) in workoutCards) {
                    if (title.lowercase(java.util.Locale.getDefault()).contains(query)) {
                        card.visibility = android.view.View.VISIBLE
                    } else {
                        card.visibility = android.view.View.GONE
                    }
                }
            }
        })

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_workout

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_achievement -> {
                    startActivity(Intent(this, AchievementActivity::class.java))
                    true
                }
                R.id.nav_workout -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}