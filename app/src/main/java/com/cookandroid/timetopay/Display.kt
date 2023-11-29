package com.cookandroid.timetopay

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Display : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val currentDate = getCurrentDate()
        val formattedDate = getString(R.string.today_date, currentDate)
        dateTextView.text = formattedDate
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}