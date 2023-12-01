package com.cookandroid.timetopay


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class Display : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.displaymain)

        //위시리스트 설정 데이터 추출
        val intent = intent
        val wishName = intent.getStringExtra("wishName")
        val wishExplain = intent.getStringExtra("wishExplain")
        val wishPrice = intent.getStringExtra("wishPrice")
        val wishtext : TextView = findViewById(R.id.wishText)
        wishtext.text = "물건 이름: $wishName\n물건 설명: $wishExplain"

        // 근무지 설정 데이터 추출
        val location = intent.getStringExtra("location")
        val opExplanation = intent.getStringExtra("opExplanation")
        val opHourlyRate = intent.getStringExtra("opHourlyRate")
        val opWeek = intent.getStringExtra("opWeek")
        val opTime = intent.getStringExtra("opTime")

        // 현재 날짜 가져오기
        val currentDate = getCurrentDate()

        // TextView에 텍스트 설정
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        dateTextView.text = "$currentDate"
    }

    // 현재 날짜를 가져오는 함수
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")  // 날짜 형식 지정
        return sdf.format(Date())
    }
}