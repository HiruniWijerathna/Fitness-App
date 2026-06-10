package com.mobilecomputing.fitnessapp

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class StartWorkoutActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var playPauseIcon: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var timerText: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var isTracking = false

    private val updateProgressAction = object : Runnable {
        override fun run() {
            if (videoView.isPlaying && !isTracking) {
                val currentPos = videoView.currentPosition
                val totalDuration = videoView.duration
                if (totalDuration > 0) {
                    val progress = (currentPos * 100f / totalDuration).toInt()
                    seekBar.progress = progress

                    val seconds = (currentPos / 1000) % 60
                    val minutes = (currentPos / 1000) / 60
                    timerText.text = String.format("%02d:%02d", minutes, seconds)
                }
            }
            handler.postDelayed(this, 500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_workout)

        val videoResId = intent.getIntExtra("VIDEO_RES_ID", R.raw.pw)

        videoView = findViewById(R.id.videoFrame)
        val btnBack = findViewById<TextView>(R.id.btnBackStart)
        val btnPlayPause = findViewById<CardView>(R.id.btnPlayPause)
        playPauseIcon = findViewById(R.id.playPauseIcon)
        seekBar = findViewById(R.id.seekBar)
        timerText = findViewById(R.id.timerText)

        btnBack.setOnClickListener { finish() }

        val nextImageResId = intent.getIntExtra("NEXT_IMAGE_RES_ID", -1)
        val nextTitle = intent.getStringExtra("NEXT_TITLE")
        val nextDesc = intent.getStringExtra("NEXT_DESC")

        val upNextSection = findViewById<android.view.View>(R.id.upNextSection)
        val upNextImage = findViewById<android.widget.ImageView>(R.id.upNextImage)
        val upNextTitle = findViewById<android.widget.TextView>(R.id.upNextTitle)
        val upNextDesc = findViewById<android.widget.TextView>(R.id.upNextDesc)

        if (nextImageResId != -1 && nextTitle != null) {
            upNextSection.visibility = android.view.View.VISIBLE
            upNextImage.setImageResource(nextImageResId)
            upNextTitle.text = nextTitle
            if (nextDesc != null) {
                upNextDesc.text = nextDesc
            }
        } else {
            upNextSection.visibility = android.view.View.GONE
        }

        val uri = Uri.parse("android.resource://$packageName/$videoResId")
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            videoView.start()
            playPauseIcon.text = "⏸"
            handler.post(updateProgressAction)
        }

        btnPlayPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                playPauseIcon.text = "▶"
            } else {
                videoView.start()
                playPauseIcon.text = "⏸"
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val totalDuration = videoView.duration
                    if (totalDuration > 0) {
                        val newPos = (progress * totalDuration) / 100
                        val seconds = (newPos / 1000) % 60
                        val minutes = (newPos / 1000) / 60
                        timerText.text = String.format("%02d:%02d", minutes, seconds)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isTracking = false
                val totalDuration = videoView.duration
                if (totalDuration > 0 && seekBar != null) {
                    val newPos = (seekBar.progress * totalDuration) / 100
                    videoView.seekTo(newPos)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateProgressAction)
    }
}
