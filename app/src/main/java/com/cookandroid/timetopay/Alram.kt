package com.cookandroid.timetopay


import android.os.Bundle


import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity

class Alram : AppCompatActivity() {
    private lateinit var timePicker: TimePicker
    private lateinit var daySpinner: Spinner
    private lateinit var setAlarmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alrammanager)

        timePicker = findViewById(R.id.timePicker)
        daySpinner = findViewById(R.id.daySpinner)
        setAlarmButton = findViewById(R.id.setAlarmButton)

        ArrayAdapter.createFromResource(
            this,
            R.array.days_of_week,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }

        setAlarmButton.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val dayOfWeek = daySpinner.selectedItemPosition + 1
            val alarmHelper = AlarmHelper(this)
            alarmHelper.setAlarm(hour, minute, dayOfWeek, 0)
        }
    }
}