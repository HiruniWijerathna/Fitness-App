//package com.mobilecomputing.fitnessapp
//
//import android.graphics.Color
//import android.net.Uri
//import android.os.Bundle
//import android.widget.*
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import android.content.Intent
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class ProfileActivity : AppCompatActivity() {
//
//    private lateinit var profileImage: ImageView
//    private lateinit var txtName: TextView
//
//    // Image picker
//    private val imagePicker =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let {
//                profileImage.setImageURI(it)
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//
//        profileImage = findViewById(R.id.profileImage)
//        txtName = findViewById(R.id.txtName)
//
//        val imgSitting = findViewById<ImageView>(R.id.imgSitting)
//        val imgWalk = findViewById<ImageView>(R.id.imgWalk)
//        val imgRun = findViewById<ImageView>(R.id.imgRun)
//        val imgExercise = findViewById<ImageView>(R.id.imgExercise)
//
//        val txtActivityTitle = findViewById<TextView>(R.id.txtActivityTitle)
//        val txtActivityDesc = findViewById<TextView>(R.id.txtActivityDesc)
//
//        val btnEdit = findViewById<Button>(R.id.btnEdit)
//        val txtHeight = findViewById<TextView>(R.id.txtHeight)
//        val txtWeight = findViewById<TextView>(R.id.txtWeight)
//        val txtBirthday = findViewById<TextView>(R.id.txtBirthday)
//
//        // default colors
//        txtHeight.setTextColor(Color.WHITE)
//        txtWeight.setTextColor(Color.WHITE)
//        txtBirthday.setTextColor(Color.WHITE)
//        txtName.setTextColor(Color.WHITE)
//
//        profileImage.setOnClickListener {
//            imagePicker.launch("image/*")
//        }
//
//        // EDIT NAME
//        btnEdit.setOnClickListener {
//
//            val view = layoutInflater.inflate(
//                R.layout.dialog_name_picker,
//                null
//            )
//
//            val editName = view.findViewById<EditText>(R.id.editName)
//            val btnDone = view.findViewById<TextView>(R.id.btnDone)
//            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)
//
//            val defaultName = getString(R.string.user_name)
//
//            if (txtName.text.toString() != defaultName) {
//                editName.hint = txtName.text.toString()
//            } else {
//                editName.hint = "Enter your name"
//            }
//
//            editName.setText("")
//
//            val dialog = AlertDialog.Builder(this)
//                .setView(view)
//                .create()
//
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(
//                android.R.color.transparent
//            )
//
//            btnDone.setOnClickListener {
//                val newName = editName.text.toString().trim()
//
//                if (newName.isNotEmpty()) {
//                    txtName.text = newName
//                }
//
//                dialog.dismiss()
//            }
//
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        // =========================
//        // 🔥 ACTIVITY LEVEL FIX
//        // =========================
//
//        fun resetActivityIcons() {
//            imgSitting.setBackgroundResource(R.drawable.activity_circle)
//            imgWalk.setBackgroundResource(R.drawable.activity_circle)
//            imgRun.setBackgroundResource(R.drawable.activity_circle)
//            imgExercise.setBackgroundResource(R.drawable.activity_circle)
//        }
//
//        // default selected = Sitting
//        resetActivityIcons()
//        imgSitting.setBackgroundResource(R.drawable.activity_circle_selected)
//
//        txtActivityTitle.text = "Sedentary"
//        txtActivityDesc.text = "Typical daily activities"
//
//        imgSitting.setOnClickListener {
//            resetActivityIcons()
//            imgSitting.setBackgroundResource(R.drawable.activity_circle_selected)
//
//            txtActivityTitle.text = "Sedentary"
//            txtActivityDesc.text = "Typical daily activities"
//        }
//
//        imgWalk.setOnClickListener {
//            resetActivityIcons()
//            imgWalk.setBackgroundResource(R.drawable.activity_circle_selected)
//
//            txtActivityTitle.text = "Somewhat Active"
//            txtActivityDesc.text = "Typical daily activities and 30-60 minutes of daily moderate activity (for example, walking at 5-7km/h)"
//        }
//
//        imgRun.setOnClickListener {
//            resetActivityIcons()
//            imgRun.setBackgroundResource(R.drawable.activity_circle_selected)
//
//            txtActivityTitle.text = "Active"
//            txtActivityDesc.text = "Typical daily activities plus at least 60 minutes of daily moderate activity"
//        }
//
//        imgExercise.setOnClickListener {
//            resetActivityIcons()
//            imgExercise.setBackgroundResource(R.drawable.activity_circle_selected)
//
//            txtActivityTitle.text = "Very Active"
//            txtActivityDesc.text = "Typical daily activities plus at least 150 minutes of daily moderate activity and 60 minutes of vigorous activity or add 120 minutes of moderate activity to your daily activities"
//        }
//
//        // HEIGHT PICKER
//        txtHeight.setOnClickListener {
//            val view = layoutInflater.inflate(R.layout.dialog_height_picker, null)
//
//            val pickerCm = view.findViewById<NumberPicker>(R.id.pickerCm)
//            val pickerDecimal = view.findViewById<NumberPicker>(R.id.pickerDecimal)
//            val btnDone = view.findViewById<TextView>(R.id.btnDone)
//            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)
//
//            pickerCm.minValue = 20
//            pickerCm.maxValue = 300
//            pickerCm.value = 170
//
//            pickerDecimal.minValue = 0
//            pickerDecimal.maxValue = 9
//            pickerDecimal.value = 0
//
//            val dialog = AlertDialog.Builder(this)
//                .setView(view)
//                .create()
//
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//            setNumberPickerTextColor(pickerCm, Color.WHITE)
//            setNumberPickerTextColor(pickerDecimal, Color.WHITE)
//
//            btnDone.setOnClickListener {
//                txtHeight.text = "${pickerCm.value}.${pickerDecimal.value} cm"
//                dialog.dismiss()
//            }
//
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        // WEIGHT PICKER
//        txtWeight.setOnClickListener {
//            val view = layoutInflater.inflate(R.layout.dialog_weight_picker, null)
//
//            val pickerKg = view.findViewById<NumberPicker>(R.id.pickerKg)
//            val pickerDecimal = view.findViewById<NumberPicker>(R.id.pickerDecimal)
//            val btnDone = view.findViewById<TextView>(R.id.btnDone)
//            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)
//
//            pickerKg.minValue = 10
//            pickerKg.maxValue = 300
//            pickerKg.value = 70
//
//            pickerDecimal.minValue = 0
//            pickerDecimal.maxValue = 9
//            pickerDecimal.value = 0
//
//            val dialog = AlertDialog.Builder(this)
//                .setView(view)
//                .create()
//
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//            setNumberPickerTextColor(pickerKg, Color.WHITE)
//            setNumberPickerTextColor(pickerDecimal, Color.WHITE)
//
//            btnDone.setOnClickListener {
//                txtWeight.text = "${pickerKg.value}.${pickerDecimal.value} kg"
//                dialog.dismiss()
//            }
//
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        // BIRTHDAY PICKER
//        txtBirthday.setOnClickListener {
//
//            val view = layoutInflater.inflate(
//                R.layout.dialog_birthday_picker,
//                null
//            )
//
//            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
//            val btnDone = view.findViewById<TextView>(R.id.btnDone)
//            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)
//
//            val dialog = AlertDialog.Builder(this)
//                .setView(view)
//                .create()
//
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//            btnDone.setOnClickListener {
//
//                val day = datePicker.dayOfMonth
//                val month = datePicker.month
//                val year = datePicker.year
//
//                val months = arrayOf(
//                    "Jan","Feb","Mar","Apr","May","Jun",
//                    "Jul","Aug","Sep","Oct","Nov","Dec"
//                )
//
//                txtBirthday.text = "$day ${months[month]} $year"
//                dialog.dismiss()
//            }
//
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        val bottomNav =
//            findViewById<BottomNavigationView>(R.id.bottomNavigation)
//
//        bottomNav.selectedItemId = R.id.nav_profile
//
//        bottomNav.setOnItemSelectedListener {
//
//            when (it.itemId) {
//
//                R.id.nav_home -> {
//
//                    startActivity(
//                        Intent(
//                            this,
//                            HomeActivity::class.java
//                        )
//                    )
//                    true
//                }
//
//                R.id.nav_achievement -> {
//
//                    startActivity(
//                        Intent(
//                            this,
//                            AchievementActivity::class.java
//                        )
//                    )
//                    true
//                }
//
//                R.id.nav_workout -> {
//
//                    startActivity(
//                        Intent(
//                            this,
//                            WorkoutActivity::class.java
//                        )
//                    )
//                    true
//                }
//
//                R.id.nav_profile -> true
//
//                else -> false
//            }
//        }
//    }
//
//    // FIXED NumberPicker text color
//    private fun setNumberPickerTextColor(numberPicker: NumberPicker, color: Int) {
//        try {
//            val field =
//                NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
//            field.isAccessible = true
//
//            val paint = field.get(numberPicker) as android.graphics.Paint
//            paint.color = color
//
//            for (i in 0 until numberPicker.childCount) {
//                val child = numberPicker.getChildAt(i)
//                if (child is EditText) {
//                    child.setTextColor(color)
//                    child.textSize = 24f
//                }
//            }
//
//            numberPicker.invalidate()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}