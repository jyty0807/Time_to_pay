package com.cookandroid.timetopay

import android.annotation.SuppressLint
import java.io.FileInputStream
import java.io.FileOutputStream

import android.view.View
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class Calendarview : AppCompatActivity() {

    var userID: String = "userID"
    lateinit var fname: String
    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3:String
    lateinit var calendarView: CalendarView
    lateinit var updateBtn: Button
    lateinit var deleteBtn:Button
    lateinit var saveBtn:Button
    lateinit var diaryTextView: TextView
    lateinit var next:TextView
    lateinit var diaryContent:TextView
    lateinit var timeContent1:TextView
    lateinit var timeContent2:TextView
    lateinit var contextEditText: EditText
    lateinit var start_time: EditText
    lateinit var end_time: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        // UI값 생성
        calendarView=findViewById(R.id.ccalendarView)
        diaryTextView=findViewById(R.id.ddiaryContent)
        next=findViewById(R.id.nnext)
        saveBtn=findViewById(R.id.ssaveBtn)
        deleteBtn=findViewById(R.id.ddeleteBtn)
        updateBtn=findViewById(R.id.uupdateBtn)
        diaryContent=findViewById(R.id.ddiaryContent)
        contextEditText=findViewById(R.id.ccontextEditText)
        start_time=findViewById(R.id.sstart_time)
        end_time=findViewById(R.id.eend_time)
        timeContent1=findViewById(R.id.ttimeContent1)
        timeContent2=findViewById(R.id.ttimeContent2)


        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            diaryTextView.visibility = View.VISIBLE
            next.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.VISIBLE
            start_time.visibility = View.VISIBLE
            end_time.visibility = View.VISIBLE
            timeContent1.visibility=View.INVISIBLE
            timeContent2.visibility=View.INVISIBLE
            diaryContent.visibility = View.INVISIBLE
            updateBtn.visibility = View.INVISIBLE
            deleteBtn.visibility = View.INVISIBLE
            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            contextEditText.setText("")
            start_time.setText("")
            end_time.setText("")
            checkDay(year, month, dayOfMonth, userID)
        }

        saveBtn.setOnClickListener {
            saveDiary(fname)
            contextEditText.visibility = View.INVISIBLE
            start_time.visibility = View.INVISIBLE
            end_time.visibility=View.INVISIBLE

            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE

            str1 = contextEditText.text.toString()
            str2 = start_time.text.toString()
            str3 = end_time.text.toString()

            diaryContent.text = str1
            diaryContent.visibility = View.VISIBLE
            next.visibility = View.VISIBLE

            timeContent1.text = str2
            timeContent1.visibility = View.VISIBLE
            timeContent2.text = str3
            timeContent2.visibility=View.VISIBLE
        }

        //시작 시간
        start_time.setOnClickListener {
            showTimePicker(start_time)
        }

        //종료 시간
        end_time.setOnClickListener {
            showTimePicker(end_time)
        }

    }

    // 달력 내용 조회, 수정
    fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
        //저장할 파일 이름설정
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        var fileInputStream: FileInputStream
        try {
            fileInputStream = openFileInput(fname)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str1 = String(fileData)
            str2 = String(fileData)
            str3 = String(fileData)

            contextEditText.visibility = View.INVISIBLE
            start_time.visibility = View.INVISIBLE
            end_time.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = str1

            timeContent1.visibility = View.VISIBLE
            timeContent1.text = str2
            timeContent2.visibility = View.VISIBLE
            timeContent2.text = str3

            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE

            updateBtn.setOnClickListener {
                contextEditText.visibility = View.VISIBLE
                start_time.visibility = View.VISIBLE
                end_time.visibility=View.VISIBLE
                contextEditText.setText(str1)
                start_time.setText(str2)
                end_time.setText(str3)

                diaryContent.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
                timeContent1.visibility = View.INVISIBLE
                timeContent2.visibility = View.INVISIBLE
                saveBtn.visibility = View.VISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryContent.text = contextEditText.text
            }

            deleteBtn.setOnClickListener {
                diaryContent.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
                timeContent1.visibility = View.INVISIBLE
                timeContent2.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                start_time.setText("")
                start_time.visibility = View.VISIBLE
                end_time.setText("")
                end_time.visibility=View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                removeDiary(fname)
            }

            if (diaryContent.text == null) {
                diaryContent.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
                timeContent1.visibility = View.INVISIBLE
                timeContent2.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                contextEditText.visibility = View.VISIBLE
                start_time.visibility = View.VISIBLE
                end_time.visibility=View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 제거
    @SuppressLint("WrongConstant")
    fun removeDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    // 달력 내용 추가
    @SuppressLint("WrongConstant")
    fun saveDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = contextEditText.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
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
}