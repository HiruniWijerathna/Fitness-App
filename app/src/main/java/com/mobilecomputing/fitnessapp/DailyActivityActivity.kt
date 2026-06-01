//package com.mobilecomputing.fitnessapp
//
//import android.app.DatePickerDialog
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.Color
//import android.graphics.drawable.GradientDrawable
//import android.graphics.Typeface
//import android.os.Bundle
//import android.view.Gravity
//import android.view.View
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.cardview.widget.CardView
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.google.android.material.progressindicator.CircularProgressIndicator
//import java.text.SimpleDateFormat
//import java.util.*
//
//class DailyActivityActivity : AppCompatActivity() {
//
//    private lateinit var prefs: SharedPreferences
//    private var selectedCal: Calendar = Calendar.getInstance()
//    private var isMonthlyView = false
//
//    private val keyFmt  = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//    private val dayNames = arrayOf("S", "M", "T", "W", "T", "F", "S")
//    private val dispFmt  = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
//
//    // day-selector container (populated programmatically)
//    private lateinit var dayContainer: LinearLayout
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_daily_activity)
//
//        prefs         = getSharedPreferences(HomeActivity.PREFS_NAME, MODE_PRIVATE)
//        dayContainer  = findViewById(R.id.dayContainer)
//
//        buildDaySelector()
//        refreshDayDetail()
//        setupChartToggle()
//        buildChart()
//        setupBottomNav()
//    }
//
//    // ─── Day Selector ─────────────────────────────────────────────────────────
//
//    /**
//     * Renders the last-7-days strip inside the HorizontalScrollView.
//     * i=0 → 6 days ago … i=6 → today
//     */
//    private fun buildDaySelector() {
//        dayContainer.removeAllViews()
//
//        // Back button wired here so it's always present
//        findViewById<TextView>(R.id.btnBackDaily).setOnClickListener { finish() }
//
//        // Calendar picker
//        findViewById<TextView>(R.id.btnCalendar).setOnClickListener { showCalendarPicker() }
//
//        for (i in 6 downTo 0) {
//            val dayCal = Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, -i) }
//            dayContainer.addView(makeDayButton(dayCal))
//        }
//
//        // Load Profile Image
//        val savedUri = prefs.getString("profile_image_uri", null)
//        if (savedUri != null) {
//            try {
//                findViewById<ImageView>(R.id.dailyProfileImage).setImageURI(android.net.Uri.parse(savedUri))
//            } catch (e: Exception) {}
//        }
//    }
//
//    private fun makeDayButton(dayCal: Calendar): View {
//        val isToday    = isSameDay(dayCal, Calendar.getInstance())
//        val isSelected = isSameDay(dayCal, selectedCal)
//
//        val view = layoutInflater.inflate(R.layout.item_day_ring, dayContainer, false)
//        val root = view.findViewById<LinearLayout>(R.id.dayRoot)
//        val letter = view.findViewById<TextView>(R.id.txtDayLetter)
//        val ringOuter = view.findViewById<CircularProgressIndicator>(R.id.ringOuterMini)
//        val ringMiddle = view.findViewById<CircularProgressIndicator>(R.id.ringMiddleMini)
//        val ringInner = view.findViewById<CircularProgressIndicator>(R.id.ringInnerMini)
//
//        // Setup text
//        letter.text = dayNames[dayCal.get(Calendar.DAY_OF_WEEK) - 1]
//        if (isSelected) {
//            root.setBackgroundResource(R.drawable.bg_day_selected)
//            letter.setTextColor(Color.WHITE)
//            letter.setTypeface(null, Typeface.BOLD)
//        } else {
//            root.background = null
//            letter.setTextColor(if (isToday) Color.parseColor("#AAFFFFFF") else Color.parseColor("#77FFFFFF"))
//            letter.setTypeface(null, Typeface.NORMAL)
//        }
//
//        // Load stats for this day to color rings
//        val dateStr = keyFmt.format(dayCal.time)
//        val steps = prefs.getInt("steps_$dateStr", 0)
//        val activeMin = steps / 100
//        val burnedKcal = (steps * 0.045).toInt()
//
//        setRingProgress(ringOuter, steps, 6000, "#4CAF50")
//        setRingProgress(ringMiddle, activeMin, 90, "#15BDD7")
//        setRingProgress(ringInner, burnedKcal, 300, "#E91E63")
//
//        val calCopy = dayCal.clone() as Calendar
//        view.setOnClickListener {
//            selectedCal = calCopy
//            buildDaySelector()
//            refreshDayDetail()
//            buildChart()
//        }
//        return view
//    }
//
//    private fun showCalendarPicker() {
//        DatePickerDialog(
//            this,
//            R.style.CustomDatePickerTheme,
//            { _, y, m, d ->
//                selectedCal = Calendar.getInstance().also { it.set(y, m, d) }
//                // If selected date is within the last-7-days window, show it there;
//                // otherwise just update details without rebuilding (date might be outside strip)
//                buildDaySelector()
//                refreshDayDetail()
//                buildChart()
//            },
//            selectedCal.get(Calendar.YEAR),
//            selectedCal.get(Calendar.MONTH),
//            selectedCal.get(Calendar.DAY_OF_MONTH)
//        ).also { picker ->
//            // Restrict to today or earlier
//            picker.datePicker.maxDate = System.currentTimeMillis()
//        }.show()
//    }
//
//    // ─── Day Detail ───────────────────────────────────────────────────────────
//
//    private fun refreshDayDetail() {
//        val dateStr = keyFmt.format(selectedCal.time)
//
//        val steps = prefs.getInt("steps_$dateStr", 0)
//        val activeMin = steps / 100
//        val burnedKcal = (steps * 0.045).toInt()
//        val km = steps * 0.000762
//        val totalBurnt = 1500 + burnedKcal
//
//        // Stat TextViews
//        findViewById<TextView>(R.id.txtDaySteps).text = "${"%,d".format(steps)}\n/6000"
//        findViewById<TextView>(R.id.txtDaySleep).text = "$activeMin\n/90 mins"
//        findViewById<TextView>(R.id.txtDayFood).text  = "$burnedKcal\n/300 kcal"
//
//        findViewById<TextView>(R.id.txtTotalCalories).text = "$totalBurnt kcal"
//        findViewById<TextView>(R.id.txtDayDistance).text = String.format(Locale.US, "%.2f km", km)
//
//        // Rings
//        val ringOuter = findViewById<CircularProgressIndicator>(R.id.dayRingOuter)
//        val ringMiddle = findViewById<CircularProgressIndicator>(R.id.dayRingMiddle)
//        val ringInner = findViewById<CircularProgressIndicator>(R.id.dayRingInner)
//
//        setRingProgress(ringOuter, steps, 6000, "#4CAF50")
//        setRingProgress(ringMiddle, activeMin, 90, "#15BDD7")
//        setRingProgress(ringInner, burnedKcal, 300, "#E91E63")
//    }
//
//    private fun setRingProgress(ring: CircularProgressIndicator, value: Int, target: Int, colorHex: String) {
//        if (value == 0) {
//            ring.progress = 0
//            ring.trackColor = Color.parseColor("#22" + colorHex.substring(1))
//            ring.setIndicatorColor(Color.parseColor(colorHex))
//            return
//        }
//        val percent = ((value.toFloat() / target.toFloat()) * 100).toInt()
//        if (percent >= 100) {
//            ring.trackColor = Color.parseColor(colorHex)
//            ring.setIndicatorColor(Color.WHITE)
//            ring.progress = if (percent % 100 == 0) 100 else percent % 100
//        } else {
//            ring.trackColor = Color.parseColor("#22" + colorHex.substring(1))
//            ring.setIndicatorColor(Color.parseColor(colorHex))
//            ring.progress = percent
//        }
//    }
//
//    // ─── Chart ────────────────────────────────────────────────────────────────
//
//    private fun setupChartToggle() {
//        val btnW = findViewById<TextView>(R.id.btnWeekly)
//        val btnM = findViewById<TextView>(R.id.btnMonthly)
//
//        fun applyState() {
//            if (isMonthlyView) {
//                btnM.setBackgroundResource(R.drawable.tab_active_bg)
//                btnM.setTextColor(Color.WHITE)
//                btnM.setTypeface(null, Typeface.BOLD)
//                btnW.background = null
//                btnW.setTextColor(Color.parseColor("#CCFFFFFF"))
//                btnW.setTypeface(null, Typeface.NORMAL)
//            } else {
//                btnW.setBackgroundResource(R.drawable.tab_active_bg)
//                btnW.setTextColor(Color.WHITE)
//                btnW.setTypeface(null, Typeface.BOLD)
//                btnM.background = null
//                btnM.setTextColor(Color.parseColor("#CCFFFFFF"))
//                btnM.setTypeface(null, Typeface.NORMAL)
//            }
//        }
//
//        btnW.setOnClickListener { isMonthlyView = false; applyState(); buildChart() }
//        btnM.setOnClickListener { isMonthlyView = true;  applyState(); buildChart() }
//        applyState()
//    }
//
//    private fun buildChart() {
//        val container = findViewById<LinearLayout>(R.id.chartContainer)
//        container.removeAllViews()
//
//        val days      = if (isMonthlyView) 30 else 7
//        val maxBarDp  = 90
//
//        // Collect steps for all days in range
//        val data = mutableListOf<Pair<Calendar, Int>>()
//        var maxSteps = 1
//        for (i in days - 1 downTo 0) {
//            val cal = Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, -i) }
//            val ds  = keyFmt.format(cal.time)
//            val s   = prefs.getInt("steps_$ds", 0)
//            data.add(cal to s)
//            if (s > maxSteps) maxSteps = s
//        }
//
//        // Update totals label
//        val total = data.sumOf { it.second }
//        val avg   = total / days
//        findViewById<TextView>(R.id.txtChartTotal).text =
//            "Avg ${"%,d".format(avg)} steps/day  •  Total ${"%,d".format(total)}"
//
//        // Bar width
//        val barW = if (isMonthlyView) dpToPx(14) else dpToPx(28)
//        val gapW = if (isMonthlyView) dpToPx(3)  else dpToPx(8)
//
//        for ((cal, steps) in data) {
//            val isSelected = isSameDay(cal, selectedCal)
//            val isToday    = isSameDay(cal, Calendar.getInstance())
//
//            val colH = 120 // dp — fixed height of the chart container
//            val barH = ((steps.toFloat() / maxSteps.toFloat()) * dpToPx(maxBarDp)).toInt()
//                           .coerceAtLeast(dpToPx(3))
//
//            // Column (label below bar)
//            val col = LinearLayout(this).apply {
//                orientation = LinearLayout.VERTICAL
//                gravity     = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
//                val lp = LinearLayout.LayoutParams(barW + gapW, dpToPx(colH))
//                layoutParams = lp
//            }
//
//            // Bar
//            val bar = View(this).apply {
//                val gd = GradientDrawable().apply {
//                    cornerRadii = floatArrayOf(
//                        dpToPx(4).toFloat(), dpToPx(4).toFloat(),
//                        dpToPx(4).toFloat(), dpToPx(4).toFloat(),
//                        0f, 0f, 0f, 0f
//                    )
//                    setColor(
//                        when {
//                            isSelected -> Color.parseColor("#15BDD7")
//                            isToday    -> Color.parseColor("#9915BDD7")
//                            else       -> Color.parseColor("#554488FF")
//                        }
//                    )
//                }
//                background = gd
//                val lp = LinearLayout.LayoutParams(barW, barH)
//                lp.bottomMargin = dpToPx(4)
//                layoutParams = lp
//            }
//
//            // Label
//            val lbl = TextView(this).apply {
//                text = if (isMonthlyView) cal.get(Calendar.DAY_OF_MONTH).toString()
//                       else dayNames[cal.get(Calendar.DAY_OF_WEEK) - 1]
//                textSize = if (isMonthlyView) 9f else 11f
//                gravity  = Gravity.CENTER
//                setTextColor(
//                    if (isSelected) Color.parseColor("#15BDD7")
//                    else            Color.parseColor("#AAFFFFFF")
//                )
//                if (isSelected) setTypeface(null, Typeface.BOLD)
//                layoutParams = LinearLayout.LayoutParams(barW + gapW, LinearLayout.LayoutParams.WRAP_CONTENT)
//            }
//
//            col.addView(bar)
//            col.addView(lbl)
//
//            // Tap bar → select that day
//            val calCopy = cal.clone() as Calendar
//            col.setOnClickListener {
//                selectedCal = calCopy
//                buildDaySelector()
//                refreshDayDetail()
//                buildChart()
//            }
//
//            container.addView(col)
//        }
//    }
//
//    // No mock data logic needed anymore
//
//    // ─── Helpers ──────────────────────────────────────────────────────────────
//
//    private fun isSameDay(a: Calendar, b: Calendar) =
//        a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
//        a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)
//
//    private fun dpToPx(dp: Int) = (dp * resources.displayMetrics.density).toInt()
//
//    // ─── Bottom Nav ───────────────────────────────────────────────────────────
//
//    private fun setupBottomNav() {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        bottomNav.selectedItemId = R.id.nav_home
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_home        -> { startActivity(Intent(this, HomeActivity::class.java)); true }
//                R.id.nav_achievement -> { startActivity(Intent(this, AchievementActivity::class.java)); true }
//                R.id.nav_workout     -> { startActivity(Intent(this, WorkoutActivity::class.java)); true }
//                R.id.nav_profile     -> { startActivity(Intent(this, ProfileActivity::class.java)); true }
//                else -> false
//            }
//        }
//    }
//}
