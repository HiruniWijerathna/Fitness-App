package com.mobilecomputing.fitnessapp

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var txtName: TextView

    // Image picker
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                profileImage.setImageURI(it)
                try {
                    val inputStream = contentResolver.openInputStream(it)
                    val file = java.io.File(filesDir, "profile_pic.jpg")
                    val outputStream = java.io.FileOutputStream(file)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()
                    getSharedPreferences(HomeActivity.PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString("profile_image_uri", file.absolutePath)
                        .apply()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profileImage)
        txtName = findViewById(R.id.txtName)

        val prefs = getSharedPreferences(HomeActivity.PREFS_NAME, MODE_PRIVATE)
        val savedName = prefs.getString("profile_name", null)
        if (savedName != null) {
            txtName.text = savedName
        }
        val savedUri = prefs.getString("profile_image_uri", null)
        if (savedUri != null) {
            try {
                profileImage.setImageURI(android.net.Uri.parse(savedUri))
            } catch (e: Exception) {}
        }

        val imgSitting = findViewById<ImageView>(R.id.imgSitting)
        val imgWalk = findViewById<ImageView>(R.id.imgWalk)
        val imgRun = findViewById<ImageView>(R.id.imgRun)
        val imgExercise = findViewById<ImageView>(R.id.imgExercise)

        val txtActivityTitle = findViewById<TextView>(R.id.txtActivityTitle)
        val txtActivityDesc = findViewById<TextView>(R.id.txtActivityDesc)

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val txtHeight = findViewById<TextView>(R.id.txtHeight)
        val txtWeight = findViewById<TextView>(R.id.txtWeight)
        val txtBirthday = findViewById<TextView>(R.id.txtBirthday)

        // default colors
        txtHeight.setTextColor(Color.WHITE)
        txtWeight.setTextColor(Color.WHITE)
        txtBirthday.setTextColor(Color.WHITE)
        txtName.setTextColor(Color.WHITE)

        val savedHeight = prefs.getString("profile_height", "170.0 cm")
        val savedWeight = prefs.getString("profile_weight", "70.0 kg")
        val savedBirthday = prefs.getString("profile_birthday", "23 May 1990")

        txtHeight.text = savedHeight
        txtWeight.text = savedWeight
        txtBirthday.text = savedBirthday

        profileImage.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // EDIT NAME
        btnEdit.setOnClickListener {

            val view = layoutInflater.inflate(
                R.layout.dialog_name_picker,
                null
            )

            val editName = view.findViewById<EditText>(R.id.editName)
            val btnDone = view.findViewById<TextView>(R.id.btnDone)
            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

            val defaultName = getString(R.string.user_name)

            if (txtName.text.toString() != defaultName) {
                editName.hint = txtName.text.toString()
            } else {
                editName.hint = "Enter your name"
            }

            editName.setText("")

            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            dialog.show()
            dialog.window?.setBackgroundDrawableResource(
                android.R.color.transparent
            )

            btnDone.setOnClickListener {
                val newName = editName.text.toString().trim()

                if (newName.isNotEmpty()) {
                    txtName.text = newName
                    // Save to SharedPreferences so HomeActivity shows it as greeting
                    getSharedPreferences(HomeActivity.PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString("profile_name", newName)
                        .apply()
                }

                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        // =========================
        // 🔥 ACTIVITY LEVEL FIX
        // =========================

        fun resetActivityIcons() {
            imgSitting.setBackgroundResource(R.drawable.activity_circle)
            imgWalk.setBackgroundResource(R.drawable.activity_circle)
            imgRun.setBackgroundResource(R.drawable.activity_circle)
            imgExercise.setBackgroundResource(R.drawable.activity_circle)
        }

        fun updateActiveLevel(level: Int) {
            resetActivityIcons()
            prefs.edit().putInt("profile_active_level", level).apply()
            when (level) {
                0 -> {
                    imgSitting.setBackgroundResource(R.drawable.activity_circle_selected)
                    txtActivityTitle.text = "Sedentary"
                    txtActivityDesc.text = "Typical daily activities"
                }
                1 -> {
                    imgWalk.setBackgroundResource(R.drawable.activity_circle_selected)
                    txtActivityTitle.text = "Somewhat Active"
                    txtActivityDesc.text = "Typical daily activities and 30-60 minutes of daily moderate activity (for example, walking at 5-7km/h)"
                }
                2 -> {
                    imgRun.setBackgroundResource(R.drawable.activity_circle_selected)
                    txtActivityTitle.text = "Active"
                    txtActivityDesc.text = "Typical daily activities plus at least 60 minutes of daily moderate activity"
                }
                3 -> {
                    imgExercise.setBackgroundResource(R.drawable.activity_circle_selected)
                    txtActivityTitle.text = "Very Active"
                    txtActivityDesc.text = "Typical daily activities plus at least 150 minutes of daily moderate activity and 60 minutes of vigorous activity or add 120 minutes of moderate activity to your daily activities"
                }
            }
        }

        val savedActiveLevel = prefs.getInt("profile_active_level", 0)
        updateActiveLevel(savedActiveLevel)

        imgSitting.setOnClickListener { updateActiveLevel(0) }
        imgWalk.setOnClickListener { updateActiveLevel(1) }
        imgRun.setOnClickListener { updateActiveLevel(2) }
        imgExercise.setOnClickListener { updateActiveLevel(3) }

        // HEIGHT PICKER
        txtHeight.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_height_picker, null)

            val pickerCm = view.findViewById<NumberPicker>(R.id.pickerCm)
            val pickerDecimal = view.findViewById<NumberPicker>(R.id.pickerDecimal)
            val btnDone = view.findViewById<TextView>(R.id.btnDone)
            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

            val currentHeightStr = txtHeight.text.toString().replace(" cm", "")
            val heightParts = currentHeightStr.split(".")

            pickerCm.minValue = 20
            pickerCm.maxValue = 300
            pickerCm.value = heightParts.getOrNull(0)?.toIntOrNull() ?: 170

            pickerDecimal.minValue = 0
            pickerDecimal.maxValue = 9
            pickerDecimal.value = heightParts.getOrNull(1)?.toIntOrNull() ?: 0

            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            setNumberPickerTextColor(pickerCm, Color.WHITE)
            setNumberPickerTextColor(pickerDecimal, Color.WHITE)

            btnDone.setOnClickListener {
                val newHeight = "${pickerCm.value}.${pickerDecimal.value} cm"
                txtHeight.text = newHeight
                prefs.edit().putString("profile_height", newHeight).apply()
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        // WEIGHT PICKER
        txtWeight.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_weight_picker, null)

            val pickerKg = view.findViewById<NumberPicker>(R.id.pickerKg)
            val pickerDecimal = view.findViewById<NumberPicker>(R.id.pickerDecimal)
            val btnDone = view.findViewById<TextView>(R.id.btnDone)
            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

            val currentWeightStr = txtWeight.text.toString().replace(" kg", "")
            val weightParts = currentWeightStr.split(".")

            pickerKg.minValue = 10
            pickerKg.maxValue = 300
            pickerKg.value = weightParts.getOrNull(0)?.toIntOrNull() ?: 70

            pickerDecimal.minValue = 0
            pickerDecimal.maxValue = 9
            pickerDecimal.value = weightParts.getOrNull(1)?.toIntOrNull() ?: 0

            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            setNumberPickerTextColor(pickerKg, Color.WHITE)
            setNumberPickerTextColor(pickerDecimal, Color.WHITE)

            btnDone.setOnClickListener {
                val newWeight = "${pickerKg.value}.${pickerDecimal.value} kg"
                txtWeight.text = newWeight
                prefs.edit().putString("profile_weight", newWeight).apply()
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        // BIRTHDAY PICKER
        txtBirthday.setOnClickListener {

            val view = layoutInflater.inflate(
                R.layout.dialog_birthday_picker,
                null
            )

            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            val btnDone = view.findViewById<TextView>(R.id.btnDone)
            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .create()

            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btnDone.setOnClickListener {

                val day = datePicker.dayOfMonth
                val month = datePicker.month
                val year = datePicker.year

                val months = arrayOf(
                    "Jan","Feb","Mar","Apr","May","Jun",
                    "Jul","Aug","Sep","Oct","Nov","Dec"
                )
                
                val newBirthday = "$day ${months[month]} $year"
                txtBirthday.text = newBirthday
                prefs.edit().putString("profile_birthday", newBirthday).apply()
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNav.selectedItemId = R.id.nav_profile

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {

                    startActivity(
                        Intent(
                            this,
                            HomeActivity::class.java
                        )
                    )
                    true
                }

                R.id.nav_achievement -> {

                    startActivity(
                        Intent(
                            this,
                            AchievementActivity::class.java
                        )
                    )
                    true
                }

                R.id.nav_workout -> {

                    startActivity(
                        Intent(
                            this,
                            WorkoutActivity::class.java
                        )
                    )
                    true
                }

                R.id.nav_profile -> true

                else -> false
            }
        }
    }

    // FIXED NumberPicker text color
    private fun setNumberPickerTextColor(numberPicker: NumberPicker, color: Int) {
        try {
            val field =
                NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
            field.isAccessible = true

            val paint = field.get(numberPicker) as android.graphics.Paint
            paint.color = color

            for (i in 0 until numberPicker.childCount) {
                val child = numberPicker.getChildAt(i)
                if (child is EditText) {
                    child.setTextColor(color)
                    child.textSize = 24f
                }
            }

            numberPicker.invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}