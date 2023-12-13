package com.cookandroid.timetopay

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1001 // 권한 요청 코드 정의
    private lateinit var startButton: Button
    private lateinit var workButton: Button
    private lateinit var calButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        startButton = findViewById(R.id.start_Button)
        calButton = findViewById(R.id.calender_Button)

        startButton.setOnClickListener {
            startButton.setBackgroundResource(R.drawable.info_button_pressed)

            val intent = Intent(this, InfoInput::class.java)
            startActivity(intent)
        }

        calButton.setOnClickListener {
            val intent = Intent(this, Calendarview::class.java)
            startActivity(intent)
        }
    }
}

