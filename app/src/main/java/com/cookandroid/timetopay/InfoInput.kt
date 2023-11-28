package com.cookandroid.timetopay

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InfoInput : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_input)

        val locationEditText: EditText = findViewById(R.id.locationEditText)
        val opExplanationEditText: EditText = findViewById(R.id.opExplanationEditText)
        val opHourlyRateEditText: EditText = findViewById(R.id.opHourlyRateEditText)
        val opWeekTextView: MultiAutoCompleteTextView = findViewById(R.id.opWeekTextView)
        val opTimeEditText: EditText = findViewById(R.id.opTimeEditText)

        val daysOfWeek = arrayOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, daysOfWeek)
        opWeekTextView.setAdapter(adapter)


            // 구분자 설정

            val startButton: Button = findViewById(R.id.startButton)
            startButton.setOnClickListener {
                // 사용자 입력 가져오기
                val location = locationEditText.text.toString()
                val opExplanation = opExplanationEditText.text.toString()
                val opHourlyRate = opHourlyRateEditText.text.toString()
                val opWeek = opWeekTextView.text.toString()
                val opTime = opTimeEditText.text.toString()

                // 입력이 비어있는지 확인
                if (location.isEmpty() || opExplanation.isEmpty() || opHourlyRate.isEmpty() || opWeek.isEmpty() || opTime.isEmpty()) {
                    // 사용자에게 모든 입력을 완료하라는 메시지 표시
                    // 예: Toast.makeText(this, "모든 입력란을 완료해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    // 모든 입력이 완료되었으면 다음 액티비티로 이동
                    val intent = Intent(this, WishList::class.java)
                    // 다음 액티비티에 전달할 데이터가 있다면 여기서 putExtra를 사용하여 추가
                    startActivity(intent)
                }
            }
        }
    }