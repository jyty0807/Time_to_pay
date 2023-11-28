package com.cookandroid.timetopay


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startButton.setBackgroundResource(R.drawable.info_button_pressed)

            val intent = Intent(this, InfoInput::class.java)

            Handler().postDelayed({
                startActivity(intent)
            }, 200)
        }
    }
}
