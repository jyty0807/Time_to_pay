package com.cookandroid.timetopay

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Work : AppCompatActivity() {

    private lateinit var editTextStartTime: EditText
    private lateinit var editTextEndTime: EditText
    private lateinit var editTextHourlyRate: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var buttonPickDates: Button
    private lateinit var textViewResult: TextView

    private val overtimeRate = 1.5
    private val nightShiftRate = 1.5
    private val simultaneousRate = 2.0
    private val weeklyWorkHoursThreshold = 15 // 주휴 수당을 받기 위한 주간 근무 시간 기준

    private val selectedDays = mutableSetOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.worktime)

        editTextStartTime = findViewById(R.id.editTextStartTime)
        editTextEndTime = findViewById(R.id.editTextEndTime)
        editTextHourlyRate = findViewById(R.id.editTextHourlyRate)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        buttonPickDates = findViewById(R.id.buttonPickDates)
        textViewResult = findViewById(R.id.textViewResult)

        editTextStartTime.setOnClickListener {
            showTimePicker(editTextStartTime)
        }

        editTextEndTime.setOnClickListener {
            showTimePicker(editTextEndTime)
        }

        buttonCalculate.setOnClickListener {
            calculatePayment()
        }

        buttonPickDates.setOnClickListener {
            showDaysDialog()
        }
    }

    private fun showTimePicker(editText: EditText) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                val selectedTime = sdf.parse("$selectedHour:$selectedMinute")
                editText.setText(sdf.format(selectedTime))
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun showDaysDialog() {
        val daysOfWeek = arrayOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

        val checkedItems = BooleanArray(daysOfWeek.size) { false }

        AlertDialog.Builder(this)
            .setTitle("요일 선택")
            .setMultiChoiceItems(daysOfWeek, null) { _, which, isChecked ->
                if (isChecked) {
                    selectedDays.add(daysOfWeek[which])
                } else {
                    selectedDays.remove(daysOfWeek[which])
                }
            }
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                handleSelectedDays()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun handleSelectedDays() {
        val selectedDaysString = selectedDays.joinToString(", ")
        textViewResult.text = "선택된 요일: $selectedDaysString"
    }


    private fun calculatePayment() {
        val startTimeStr = editTextStartTime.text.toString()
        val endTimeStr = editTextEndTime.text.toString()

        if (startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
            textViewResult.text = "근무 시작 시간과 종료 시간을 입력해주세요."
            return
        }

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startTime = sdf.parse(startTimeStr)
        val endTime = sdf.parse(endTimeStr)

        val totalHours = (endTime.time - startTime.time) / (60 * 60 * 1000.0)

        var totalPayment = 0.0

        // 사용자가 입력한 시급 가져오기
        val hourlyRateStr = editTextHourlyRate.text.toString()
        val hourlyRate = if (hourlyRateStr.isNotEmpty()) hourlyRateStr.toDouble() else 0.0

        for (hour in 0 until totalHours.toInt()) {
            val currentHourDate = Date(startTime.time + hour * 60 * 60 * 1000)
            val currentHour = sdf.format(currentHourDate)
            val isNightShift = sdf.parse(currentHour).hours >= 22 || sdf.parse(currentHour).hours < 6
            val isOvertime = hour >= 8

            var hourlyRateMultiplier = 1.0

            if (isNightShift && isOvertime) {
                hourlyRateMultiplier = simultaneousRate
            } else if (isNightShift) {
                hourlyRateMultiplier = nightShiftRate
            } else if (isOvertime) {
                hourlyRateMultiplier = overtimeRate
            }

            totalPayment += hourlyRate * hourlyRateMultiplier * selectedDays.size
        }

        // 주휴 수당 계산 추가
        if (totalHours >= weeklyWorkHoursThreshold) {
            val weeklyBonus = hourlyRate * selectedDays.size
            totalPayment += weeklyBonus
        }

        textViewResult.text = "총 급여: $totalPayment 원"
    }
}

