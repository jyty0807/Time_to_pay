package com.cookandroid.timetopay

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AlarmTimeViewActivity : AppCompatActivity() {

    private lateinit var alarmSetTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alramtimeviewactivity)

        alarmSetTime = findViewById(R.id.tv_alarm_set_day_time)

        val intent = intent
        val setDay = intent.getIntExtra("setDay", 0)
        val day: String = when (setDay) {
            0 -> "err"
            1 -> "일요일"
            2 -> "월요일"
            3 -> "화요일"
            4 -> "수요일"
            5 -> "목요일"
            6 -> "금요일"
            7 -> "토요일"
            else -> "err"
        }

        val setTime = day + "요일 \n" + "알람 셋팅 시간" + intent.getStringExtra("setTime")
        alarmSetTime.text = setTime
    }
}