package com.cookandroid.timetopay

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private lateinit var remainHoursTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.displaymain)

        // Initialize views
        initializeViews()

        // Set up UI visibility
        setUpUIVisibility()

        // Set listeners for buttons
        setButtonListeners()

        // Retrieve and display intent data
        displayIntentData()

        // Set up achievement progress bar color
        setUpProgressBarColor()
    }

    private fun initializeViews() {
        wishimage = findViewById(R.id.wishimage)
        image = findViewById(R.id.image)
        call = findViewById(R.id.cal)
        iden = findViewById(R.id.idenn)
        cri = findViewById(R.id.crii)
        monthlySalaryTextView = findViewById(R.id.monthlysalaryTextView)
        remainingWorkDayTextView = findViewById(R.id.remainingworkdayTextView)
        remainHoursTextView = findViewById(R.id.remainHoursTextView)
    }

    private fun setUpUIVisibility() {
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
    }

    private fun setButtonListeners() {
        wishimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)
        }

        call.setOnClickListener {
            val intent = Intent(this, Calendarview::class.java)
            startActivity(intent)
        }

        iden.setOnClickListener {
            val intent = Intent(this, Scrollview::class.java)
            startActivity(intent)
        }

        cri.setOnClickListener {
            val intent = Intent(this, Scrollview::class.java)
            startActivity(intent)
        }
    }

    private fun displayIntentData() {
        val intent = intent
        val wishName = intent.getStringExtra("wishName")
        val wishExplain = intent.getStringExtra("wishExplain")
        val wishPrice = intent.getStringExtra("wishPrice")
        val wishTextView: TextView = findViewById(R.id.wishTextView)
        wishTextView.text = "물건 이름: $wishName\n물건 설명: $wishExplain"

        val location = intent.getStringExtra("location")
        val opExplanation = intent.getStringExtra("opExplanation")
        val opHourlyRate = intent.getStringExtra("opHourlyRate")
        val totalPayment = intent.getDoubleExtra("totalPayment", 0.0)

        monthlySalaryTextView.text = totalPayment.toString()

        val currentDate = getCurrentDate()
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        dateTextView.text = currentDate

        updateRemainingWorkingDays()
        updateRemainingTime()

        val achievementRate = calculateAchievementRate(wishPrice?.toDoubleOrNull() ?: 0.0, totalPayment)
        val achievementCircularProgressBar: ProgressBar = findViewById(R.id.achievementCircularProgressBar)
        achievementCircularProgressBar.progress = achievementRate.toInt()
    }

    private fun setUpProgressBarColor() {
        val progressBar: ProgressBar = findViewById(R.id.achievementCircularProgressBar)
        if (progressBar != null) {  // Null 체크 추가
            val color = ContextCompat.getColor(this, R.color.bgreen) // 원하는 색상
            progressBar.indeterminateDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            progressBar.progressDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun calculateAchievementRate(wishPrice: Double, totalPayment: Double): Double {
        if (wishPrice == 0.0) return 0.0
        val rate = (totalPayment / wishPrice) * 100
        return if (rate > 100) 100.0 else rate
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    private fun updateRemainingWorkingDays() {
        val intent = intent
        if (intent != null) {
            val remainingWorkingDays = intent.getIntExtra("remainingWorkDays", 0)
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
            if (remainingMillis > 0) {
                val remainingHours = remainingMillis / (60 * 60 * 1000)
                val remainingMinutes = (remainingMillis % (60 * 60 * 1000)) / (60 * 1000)
                remainHoursTextView.text = "${remainingHours}"
            } else {
                remainHoursTextView.text = "목표 달성"
            }
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
