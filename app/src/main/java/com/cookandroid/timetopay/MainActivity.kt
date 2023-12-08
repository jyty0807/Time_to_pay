package com.cookandroid.timetopay


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var workButton: Button
    private lateinit var calButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }

        startButton = findViewById(R.id.start_Button)

        startButton.setOnClickListener {
            startButton.setBackgroundResource(R.drawable.info_button_pressed)

            val intent = Intent(this, InfoInput::class.java)

            Handler().postDelayed({
                startActivity(intent)
            }, 200)


            calButton = findViewById(R.id.calender_Button)

            calButton.setOnClickListener {

                val intent = Intent(this, Calendarview::class.java)

                Handler().postDelayed({
                    startActivity(intent)
                }, 200)
            }

        }
    }
}
