package com.cookandroid.timetopay

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InfoInput : AppCompatActivity() {

    private lateinit var locationEditText: EditText
    private lateinit var opExplanationEditText: EditText
    private lateinit var opHourlyRateEditText: EditText
    private lateinit var doneButton: Button
    private lateinit var opStartTimeEditText: EditText
    private lateinit var opEndTimeEditText: EditText
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
        setContentView(R.layout.info_input)

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

        locationEditText = findViewById(R.id.locationEditText)
        opExplanationEditText = findViewById(R.id.opExplanationEditText)
        opHourlyRateEditText = findViewById(R.id.opHourlyRateEditText)
        doneButton = findViewById(R.id.doneButton)
        opStartTimeEditText = findViewById(R.id.opStartTimeEditText)
        opEndTimeEditText = findViewById(R.id.opEndTimeEditText)
        buttonPickDates = findViewById(R.id.buttonPickDates)
        textViewResult = findViewById(R.id.textViewResult)

        opStartTimeEditText.setOnClickListener {
            showTimePicker(opStartTimeEditText)
        }

        opEndTimeEditText.setOnClickListener {
            showTimePicker(opEndTimeEditText)
        }

        buttonPickDates.setOnClickListener {
            showDaysDialog()
        }

        doneButton.setOnClickListener {
            val location = locationEditText.text.toString()
            val opExplanation = opExplanationEditText.text.toString()
            val opHourlyRate = opHourlyRateEditText.text.toString()

            if (location.isEmpty() || opExplanation.isEmpty() || opHourlyRate.isEmpty()) {
                // 사용자에게 모든 입력을 완료하라는 메시지 표시
                // 예: Toast.makeText(this, "모든 입력란을 완료해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, WishList::class.java)
                intent.putExtra("location", location)
                intent.putExtra("opExplanation", opExplanation)
                intent.putExtra("opHourlyRate", opHourlyRate)
                Handler().postDelayed({
                    startActivity(intent)
                }, 200)
            }
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

                // 시간 선택 후 키보드를 숨김
                hideKeyboard(editText)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showDaysDialog() {
        val daysOfWeek = arrayOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

        androidx.appcompat.app.AlertDialog.Builder(this)
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
        val startTimeStr = opStartTimeEditText.text.toString()
        val endTimeStr = opEndTimeEditText.text.toString()
        val HourlyRate = opHourlyRateEditText.text.toString().toDoubleOrNull()


        if (startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
            textViewResult.text = "근무 시작 시간과 종료 시간을 입력해주세요."
            return
        }

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startTime = sdf.parse(startTimeStr)
        val endTime = sdf.parse(endTimeStr)

        val totalHours =
            (endTime.time - startTime.time) / (60 * 60 * 1000.0) // 총 근무 시간(시간 단위, 소수점 포함)

        var totalPayment = 0.0

        if (HourlyRate != null) {
            for (hour in 0 until totalHours.toInt()) {
                val currentHourDate = Date(startTime.time + hour * 60 * 60 * 1000)
                val currentHour = sdf.format(currentHourDate)
                val isNightShift =
                    sdf.parse(currentHour).hours >= 22 || sdf.parse(currentHour).hours < 6
                val isOvertime = hour >= 8

                var hourlyRateMultiplier = 1.0

                if (isNightShift && isOvertime) {
                    hourlyRateMultiplier = simultaneousRate
                } else if (isNightShift) {
                    hourlyRateMultiplier = nightShiftRate
                } else if (isOvertime) {
                    hourlyRateMultiplier = overtimeRate
                }

                totalPayment += HourlyRate * hourlyRateMultiplier * selectedDays.size * 4
            }
        }

        val intent = Intent(this, WishList::class.java)
        intent.putExtra("totalPayment", totalPayment)

    }
}

