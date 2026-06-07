package com.mobilecomputing.fitnessapp

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var prefs: SharedPreferences
    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // UI references
    private lateinit var txtGreeting: TextView
    private lateinit var ringCalories: CircularProgressIndicator
    private lateinit var ringSteps: CircularProgressIndicator
    private lateinit var ringActive: CircularProgressIndicator
    private lateinit var txtCaloriesStats: TextView
    private lateinit var txtStepsStats: TextView
    private lateinit var txtActiveStats: TextView
    private lateinit var progressSleep: LinearProgressIndicator
    private lateinit var txtSleepValue: TextView
    private lateinit var progressFood: LinearProgressIndicator
    private lateinit var txtFoodValue: TextView
    private lateinit var progressBody: LinearProgressIndicator
    private lateinit var txtBodyValue: TextView
    private lateinit var progressBp: LinearProgressIndicator
    private lateinit var txtBpValue: TextView
    private lateinit var progressWater: LinearProgressIndicator
    private lateinit var txtWaterValue: TextView
    private lateinit var txtWaterGlasses: TextView

    companion object {
        const val PREFS_NAME = "fitness_app_prefs"
        const val STEP_PERMISSION_REQUEST = 100
        const val MAX_WATER_ML = 2000
        const val WATER_STEP_ML = 250
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        initViews()
        setupClickListeners()
        setupSensor()
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        // Reload data every time we come back (e.g. after name change in Profile)
        loadData()
        stepSensor?.let { sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    private fun getToday(): String = dateFormat.format(Date())

    private fun initViews() {
        txtGreeting      = findViewById(R.id.txtGreeting)
        ringCalories     = findViewById(R.id.ringCalories)
        ringSteps        = findViewById(R.id.ringSteps)
        ringActive       = findViewById(R.id.ringActive)
        txtCaloriesStats = findViewById(R.id.txtCaloriesStats)
        txtStepsStats    = findViewById(R.id.txtStepsStats)
        txtActiveStats   = findViewById(R.id.txtActiveStats)
        progressSleep    = findViewById(R.id.progressSleep)
        txtSleepValue    = findViewById(R.id.txtSleepValue)
        progressFood     = findViewById(R.id.progressFood)
        txtFoodValue     = findViewById(R.id.txtFoodValue)
        progressBody     = findViewById(R.id.progressBody)
        txtBodyValue     = findViewById(R.id.txtBodyValue)
        progressBp       = findViewById(R.id.progressBp)
        txtBpValue       = findViewById(R.id.txtBpValue)
        progressWater    = findViewById(R.id.progressWater)
        txtWaterValue    = findViewById(R.id.txtWaterValue)
        txtWaterGlasses  = findViewById(R.id.txtWaterGlasses)
    }

    // ─── Data Loading ─────────────────────────────────────────────────────────

    private fun loadData() {
        val today = getToday()

        // Greeting with name from SharedPrefs (set by ProfileActivity)
        val name = prefs.getString("profile_name", "User") ?: "User"
        txtGreeting.text = "Hello, $name!"

        // Profile image
        val savedUri = prefs.getString("profile_image_uri", null)
        if (savedUri != null) {
            try {
                findViewById<ImageView>(R.id.homeProfileImage).setImageURI(android.net.Uri.parse(savedUri))
            } catch (e: Exception) {}
        }

        // Sleep
        val sleepMin = prefs.getInt("sleep_min_$today", 0)
        updateSleepUI(sleepMin)

        // Food
        val foodKcal = prefs.getInt("food_kcal_$today", 0)
        updateFoodUI(foodKcal)

        // Body composition
        val bodyFat = prefs.getInt("body_fat_$today", 0)
        updateBodyUI(bodyFat)

        // Blood pressure
        val sys = prefs.getInt("bp_sys_$today", 0)
        val dia = prefs.getInt("bp_dia_$today", 0)
        updateBpUI(sys, dia)

        // Water
        val waterMl = prefs.getInt("water_ml_$today", 0)
        updateWaterUI(waterMl)

        // Steps (from prefs — sensor will override live)
        val steps = prefs.getInt("steps_$today", 0)
        updateStepsUI(steps)
    }

    // ─── UI Updaters ──────────────────────────────────────────────────────────

    private fun updateSleepUI(totalMin: Int) {
        val h = totalMin / 60
        val m = totalMin % 60
        if (totalMin == 0) {
            txtSleepValue.text = "-- h --m"
            progressSleep.progress = 0
        } else {
            txtSleepValue.text = "${h}h ${m}m"
            progressSleep.progress = ((totalMin.toFloat() / 480f) * 100).toInt().coerceIn(0, 100)
        }
    }

    private fun updateFoodUI(kcal: Int) {
        if (kcal == 0) {
            txtFoodValue.text = "-- kcal"
            progressFood.progress = 0
        } else {
            txtFoodValue.text = "$kcal kcal"
            progressFood.progress = ((kcal.toFloat() / 2000f) * 100).toInt().coerceIn(0, 100)
        }
    }

    private fun updateBodyUI(fat: Int) {
        if (fat == 0) {
            txtBodyValue.text = "-- % fat"
            progressBody.progress = 0
        } else {
            txtBodyValue.text = "$fat% fat"
            progressBody.progress = ((fat.toFloat() / 40f) * 100).toInt().coerceIn(0, 100)
        }
    }

    private fun updateBpUI(sys: Int, dia: Int) {
        if (sys == 0) {
            txtBpValue.text = "--/-- mmHg"
            progressBp.progress = 75
        } else {
            txtBpValue.text = "$sys/$dia mmHg"
            progressBp.progress = ((sys.toFloat() / 160f) * 100).toInt().coerceIn(0, 100)
        }
    }

    private fun updateWaterUI(ml: Int) {
        txtWaterValue.text = "$ml ml"
        progressWater.progress = ((ml.toFloat() / MAX_WATER_ML.toFloat()) * 100).toInt().coerceIn(0, 100)
        val glasses = ml / WATER_STEP_ML
        txtWaterGlasses.text = "$glasses / 8 glasses"
    }

    private fun updateStepsUI(steps: Int) {
        // Steps
        txtStepsStats.text = "  $steps steps"

        // Active time (assume 1 min per 100 steps)
        val activeMin = steps / 100
        val h = activeMin / 60
        val m = activeMin % 60
        if (activeMin == 0) {
            txtActiveStats.text = "  -- time"
        } else {
            txtActiveStats.text = if (h > 0) "  ${h}h ${m}m time" else "  ${m}m time"
        }

        // Burned kcal (assume 0.045 kcal per step)
        val burnedKcal = (steps * 0.045).toInt()
        if (burnedKcal == 0) {
            txtCaloriesStats.text = "  -- kcal"
        } else {
            txtCaloriesStats.text = "  $burnedKcal kcal"
        }

        setRingProgress(ringSteps, steps, 6000, "#4CAF50")
        setRingProgress(ringActive, activeMin, 90, "#15BDD7")
        setRingProgress(ringCalories, burnedKcal, 300, "#E91E63")
    }

    private fun setRingProgress(ring: CircularProgressIndicator, value: Int, target: Int, colorHex: String) {
        if (value == 0) {
            ring.progress = 0
            ring.trackColor = Color.parseColor("#22" + colorHex.substring(1))
            ring.setIndicatorColor(Color.parseColor(colorHex))
            return
        }
        val percent = ((value.toFloat() / target.toFloat()) * 100).toInt()
        if (percent >= 100) {
            ring.trackColor = Color.parseColor(colorHex)
            ring.setIndicatorColor(Color.WHITE)
            ring.progress = if (percent % 100 == 0) 100 else percent % 100
        } else {
            ring.trackColor = Color.parseColor("#22" + colorHex.substring(1))
            ring.setIndicatorColor(Color.parseColor(colorHex))
            ring.progress = percent
        }
    }

    // ─── Click Listeners ──────────────────────────────────────────────────────

    private fun setupClickListeners() {
        // Daily balance card → Daily Activity page (normal click)
        val balanceCard = findViewById<MaterialCardView>(R.id.cardDailyBalance)
        balanceCard.setOnClickListener {
            startActivity(Intent(this, DailyActivityActivity::class.java))
        }

        // Hidden testing feature: Long press the card to manually add 500 steps!
        balanceCard.setOnLongClickListener {
            val today = getToday()
            val currentSteps = prefs.getInt("steps_$today", 0)
            val newSteps = currentSteps + 500
            prefs.edit().putInt("steps_$today", newSteps).apply()
            updateStepsUI(newSteps)
            Toast.makeText(this, "Testing: Added 500 steps! 🏃", Toast.LENGTH_SHORT).show()
            true
        }

        // Profile image → Profile page
        findViewById<androidx.cardview.widget.CardView>(R.id.homeProfileImageCard).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Metric Enter buttons
        findViewById<TextView>(R.id.btnSleepEnter).setOnClickListener { showSleepDialog() }
        findViewById<TextView>(R.id.btnFoodEnter).setOnClickListener  { showFoodDialog()  }
        findViewById<TextView>(R.id.btnBodyEnter).setOnClickListener  { showBodyDialog()  }
        findViewById<TextView>(R.id.btnBpEnter).setOnClickListener    { showBpDialog()    }

        // Water +250ml button
        findViewById<TextView>(R.id.btnAddWater).setOnClickListener {
            val today = getToday()
            val current = prefs.getInt("water_ml_$today", 0)
            if (current >= MAX_WATER_ML) {
                Toast.makeText(this, "🎉 Daily water goal already reached!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newVal = (current + WATER_STEP_ML).coerceAtMost(MAX_WATER_ML)
            prefs.edit().putInt("water_ml_$today", newVal).apply()
            updateWaterUI(newVal)
            if (newVal >= MAX_WATER_ML) {
                Toast.makeText(this, "🎉 Daily water goal reached!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ─── Dialogs ──────────────────────────────────────────────────────────────

    private fun showSleepDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_enter_sleep, null)
        val pickerH = view.findViewById<NumberPicker>(R.id.pickerHours)
        val pickerM = view.findViewById<NumberPicker>(R.id.pickerMinutes)
        val btnDone   = view.findViewById<TextView>(R.id.btnDone)
        val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

        // Hours 0-23
        pickerH.minValue = 0
        pickerH.maxValue = 23

        // Minutes in 5-min steps: 00, 05, 10, … 55
        val minLabels = Array(12) { i -> if (i == 0) "00" else (i * 5).toString().padStart(2, '0') }
        pickerM.minValue = 0
        pickerM.maxValue = 11
        pickerM.displayedValues = minLabels

        // Pre-fill current value
        val today = getToday()
        val stored = prefs.getInt("sleep_min_$today", 0)
        pickerH.value = if (stored > 0) (stored / 60).coerceIn(0, 23) else 7
        pickerM.value = if (stored > 0) ((stored % 60) / 5).coerceIn(0, 11) else 6

        setPickerColor(pickerH, Color.WHITE)
        setPickerColor(pickerM, Color.WHITE)

        val dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnDone.setOnClickListener {
            val totalMin = pickerH.value * 60 + pickerM.value * 5
            prefs.edit().putInt("sleep_min_$today", totalMin).apply()
            updateSleepUI(totalMin)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    private fun showFoodDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_enter_food, null)
        val editCal   = view.findViewById<EditText>(R.id.editCalories)
        val btnDone   = view.findViewById<TextView>(R.id.btnDone)
        val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

        val today = getToday()
        val stored = prefs.getInt("food_kcal_$today", 0)
        if (stored > 0) editCal.setText(stored.toString())

        val dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnDone.setOnClickListener {
            val kcal = editCal.text.toString().toIntOrNull() ?: 0
            prefs.edit().putInt("food_kcal_$today", kcal).apply()
            updateFoodUI(kcal)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    private fun showBodyDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_enter_body, null)
        val picker    = view.findViewById<NumberPicker>(R.id.pickerBodyFat)
        val btnDone   = view.findViewById<TextView>(R.id.btnDone)
        val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

        picker.minValue = 1
        picker.maxValue = 60
        val today = getToday()
        picker.value = prefs.getInt("body_fat_$today", 22)
        setPickerColor(picker, Color.WHITE)

        val dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnDone.setOnClickListener {
            prefs.edit().putInt("body_fat_$today", picker.value).apply()
            updateBodyUI(picker.value)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    private fun showBpDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_enter_bp, null)
        val editSys   = view.findViewById<EditText>(R.id.editSystolic)
        val editDia   = view.findViewById<EditText>(R.id.editDiastolic)
        val btnDone   = view.findViewById<TextView>(R.id.btnDone)
        val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

        val today = getToday()
        val sys = prefs.getInt("bp_sys_$today", 120)
        val dia = prefs.getInt("bp_dia_$today", 80)
        editSys.setText(sys.toString())
        editDia.setText(dia.toString())

        val dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnDone.setOnClickListener {
            val s = editSys.text.toString().toIntOrNull() ?: 120
            val d = editDia.text.toString().toIntOrNull() ?: 80
            prefs.edit().putInt("bp_sys_$today", s).putInt("bp_dia_$today", d).apply()
            updateBpUI(s, d)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    // ─── Step Counter Sensor ──────────────────────────────────────────────────

    private fun setupSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    STEP_PERMISSION_REQUEST
                )
                return
            }
        }
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STEP_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_STEP_COUNTER) return

        val totalSteps = event.values[0].toLong()
        val today = getToday()
        val baselineKey = "steps_baseline_$today"

        // First reading of the day → save as baseline
        val baseline = prefs.getLong(baselineKey, -1L)
        if (baseline == -1L) {
            prefs.edit().putLong(baselineKey, totalSteps).apply()
            updateStepsUI(0)
        } else {
            val daily = (totalSteps - baseline).toInt().coerceAtLeast(0)
            prefs.edit().putInt("steps_$today", daily).apply()
            updateStepsUI(daily)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private fun setPickerColor(picker: NumberPicker, color: Int) {
        try {
            val field = NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
            field.isAccessible = true
            (field.get(picker) as android.graphics.Paint).color = color
            for (i in 0 until picker.childCount) {
                val child = picker.getChildAt(i)
                if (child is EditText) { child.setTextColor(color); child.textSize = 20f }
            }
            picker.invalidate()
        } catch (e: Exception) { e.printStackTrace() }
    }

    // ─── Bottom Nav ───────────────────────────────────────────────────────────

    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home        -> true
                R.id.nav_achievement -> { startActivity(Intent(this, AchievementActivity::class.java)); true }
                R.id.nav_workout     -> { startActivity(Intent(this, WorkoutActivity::class.java)); true }
                R.id.nav_profile     -> { startActivity(Intent(this, ProfileActivity::class.java)); true }
                else -> false
            }
        }
    }
}