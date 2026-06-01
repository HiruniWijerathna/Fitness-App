//package com.mobilecomputing.fitnessapp
//
//import android.os.Bundle
//import android.os.CountDownTimer
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.cardview.widget.CardView
//
//class StartWorkoutActivity : AppCompatActivity() {
//
//    private var isPlaying = true
//    private var timer: CountDownTimer? = null
//    private var remainingMs: Long = 90_000L // 1:30
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_start_workout)
//
//        val btnBack = findViewById<TextView>(R.id.btnBackStart)
//        val btnPlayPause = findViewById<CardView>(R.id.btnPlayPause)
//        val playPauseIcon = findViewById<TextView>(R.id.playPauseIcon)
//        val timerText = findViewById<TextView>(R.id.timerText)
//
//        btnBack.setOnClickListener { finish() }
//
//        startTimer(timerText)
//
//        btnPlayPause.setOnClickListener {
//            if (isPlaying) {
//                timer?.cancel()
//                playPauseIcon.text = "▶"
//            } else {
//                startTimer(timerText)
//                playPauseIcon.text = "⏸"
//            }
//            isPlaying = !isPlaying
//        }
//    }
//
//    private fun startTimer(timerText: TextView) {
//        timer?.cancel()
//        timer = object : CountDownTimer(remainingMs, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                remainingMs = millisUntilFinished
//                val minutes = millisUntilFinished / 1000 / 60
//                val seconds = (millisUntilFinished / 1000) % 60
//                timerText.text = String.format("%02d:%02d", minutes, seconds)
//            }
//            override fun onFinish() {
//                timerText.text = "00:00"
//            }
//        }.start()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        timer?.cancel()
//    }
//}
