package com.cookandroid.timetopay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Display : AppCompatActivity() {

    private lateinit var wishimage: Button
    private lateinit var image: ImageView
    private val OPEN_GALLERY = 1
    private lateinit var monthlySalaryTextView: TextView
    private lateinit var call: Button
    private lateinit var iden: Button
    private lateinit var cri: Button
    private lateinit var remainingWorkDayTextView: TextView
    private lateinit var remainHoursTextView: TextView  // 남은 시간을 표시할 TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.displaymain)

        wishimage = findViewById(R.id.wishimage)
        image = findViewById(R.id.image)

        wishimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)
        }

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

        call = findViewById(R.id.cal)
        call.setOnClickListener {
            val intent = Intent(this, Calendarview::class.java)
            startActivity(intent)
        }

        iden = findViewById(R.id.idenn)
        iden.setOnClickListener {
            val intent = Intent(this, Scrollview::class.java)
            startActivity(intent)
        }

        cri = findViewById(R.id.crii)
        cri.setOnClickListener {
            val intent = Intent(this, Scrollview::class.java)
            startActivity(intent)
        }

        val intent = intent
        val wishName = intent.getStringExtra("wishName")
        val wishExplain = intent.getStringExtra("wishExplain")
        val wishPrice = intent.getStringExtra("wishPrice")
        val wishTextView: TextView = findViewById(R.id.wishTextView)
        wishTextView.text = "물건 이름: $wishName\n물건 설명: $wishExplain"

        val location = intent.getStringExtra("location")
        val opExplanation = intent.getStringExtra("opExplanation")
        val opHourlyRate = intent.getStringExtra("opHourlyRate")
        val opWeek = intent.getStringExtra("opWeek")
        val opTime = intent.getStringExtra("opTime")
        val totalPayment = intent.getDoubleExtra("totalPayment", 0.0)

        val hourlyRate = opHourlyRate?.toDoubleOrNull()
        val itemPrice = wishPrice?.toDoubleOrNull()

        if (hourlyRate != null && itemPrice != null) {
            val hoursRequired = itemPrice / hourlyRate
            intent.putExtra("hoursRequired", hoursRequired)
        }

        val monthlySalaryTextView = findViewById<TextView>(R.id.monthlysalaryTextView)
        monthlySalaryTextView.text = totalPayment.toString()

        val currentDate = getCurrentDate()

        val dateTextView: TextView = findViewById(R.id.dateTextView)
        dateTextView.text = "$currentDate"

        remainingWorkDayTextView = findViewById(R.id.remainingworkdayTextView)
        remainHoursTextView = findViewById(R.id.remainHoursTextView)

        // 남은 근무일 수를 표시할 TextView를 초기화하고 업데이트
        updateRemainingWorkingDays()

        // 남은 시간을 표시할 TextView를 초기화하고 업데이트
        updateRemainingTime()
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    private fun updateRemainingWorkingDays() {
        val intent = intent
        if (intent != null) {
            val remainingWorkingDays = intent.getIntExtra("remainingWorkDays", 0)
            // 남은 근무일 수를 TextView에 표시
            remainingWorkDayTextView.text = "$remainingWorkingDays"
        }
    }

    private fun updateRemainingTime() {
        val intent = intent
        if (intent != null) {
            val hoursRequired = intent.getDoubleExtra("hoursRequired", 0.0)

            val currentTimeMillis = System.currentTimeMillis()
            val endTimeMillis = currentTimeMillis + (hoursRequired * 60 * 60 * 1000).toLong()

            val remainingMillis = endTimeMillis - currentTimeMillis
            val remainingHours = remainingMillis / (60 * 60 * 1000)
            val remainingMinutes = (remainingMillis % (60 * 60 * 1000)) / (60 * 1000)

            // 남은 시간을 TextView에 표시
            remainHoursTextView.text = "${remainingHours}"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var imageData: Uri? = data?.data
                Toast.makeText(this, imageData.toString(), Toast.LENGTH_SHORT).show()
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageData)
                    image.setImageBitmap(bitmap)
                    wishimage.visibility = View.INVISIBLE
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
