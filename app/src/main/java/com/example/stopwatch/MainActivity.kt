package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var flag = true
    private var currentSecond = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            // Update UI or perform any action needed for stopwatch
            currentSecond++
            // Example: Log the current second
            val hour = currentSecond / 3600
            val min = (currentSecond % 3600) / 60
            val sec = currentSecond % 60
            val time = String.format("%02d:%02d:%02d", hour, min, sec)
            //println("Current Time: $time")

            // Set the formatted time to the appropriate UI element
            binding.Time.text = time

            // Repeat the task after 1 second
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        // Set click listeners for buttons
        binding.start.setOnClickListener {
            startStopwatch()
        }
        binding.pause.setOnClickListener {
            pauseStopwatch()
        }
        binding.reset.setOnClickListener {
            resetStopwatch()
        }
    }

    private fun startStopwatch() {
        // Start the stopwatch when the start button is clicked
        if(flag){
            handler.post(runnable)
            flag = false
            binding.start.isEnabled = false
            binding.pause.isEnabled = true
            binding.reset.isEnabled = true
        }
    }

    private fun pauseStopwatch() {
        // Stop the stopwatch when the pause button is clicked
        handler.removeCallbacks(runnable)
        flag = true
        binding.start.isEnabled = true
        binding.pause.isEnabled = false
        binding.reset.isEnabled = true
    }

    private fun resetStopwatch() {
        // Reset the stopwatch when the reset button is clicked
        currentSecond = 0
        binding.Time.text = "00:00:00"
        flag = true
        binding.start.isEnabled = true
        binding.pause.isEnabled = false
        binding.reset.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the stopwatch when the activity is destroyed
        handler.removeCallbacks(runnable)
    }
}
