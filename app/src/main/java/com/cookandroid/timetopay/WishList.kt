package com.cookandroid.timetopay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WishList : AppCompatActivity() {

    private lateinit var wishNameEditText: EditText
    private lateinit var wishExplainEditText: EditText
    private lateinit var wishPriceEditText: EditText
    private lateinit var wishlistButton: Button

    private var savedData: String = ""  // 추출한 데이터를 저장할 변수

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enterwishlist)

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

        val location = intent.getStringExtra("location")
        val opExplanation = intent.getStringExtra("opExplanation")
        val opHourlyRate = intent.getStringExtra("opHourlyRate")
        val totalPayment = intent.getDoubleExtra("totalPayment", 0.0)

        wishNameEditText = findViewById(R.id.wishNameEditText)
        wishExplainEditText = findViewById(R.id.wishExplainEditText)
        wishPriceEditText = findViewById(R.id.wishPriceEditText)

        wishlistButton = findViewById(R.id.wishlistbutton)
        wishlistButton.setOnClickListener {
            val wishName = wishNameEditText.text.toString()
            val wishExplain = wishExplainEditText.text.toString()
            val wishPrice = wishPriceEditText.text.toString()

            if (wishName.isEmpty() || wishExplain.isEmpty() || wishPrice.isEmpty()) {
                showToast("모든 항목을 입력하세요.")
            } else {
                // 이 부분에서 남은 근무일 수를 가져와 변수에 저장하고, 인텐트에 추가합니다.
                val remainingWorkDays = 10 // 여기에 실제로 남은 근무일 수를 계산하여 넣어주세요.

                saveData(wishName, wishExplain, wishPrice)
                showToast("물건 이름: $wishName\n물건 설명: $wishExplain\n물건 가격: $wishPrice")

                val intent = Intent(this, Display::class.java)
                intent.putExtra("wishName", wishName)
                intent.putExtra("wishExplain", wishExplain)
                intent.putExtra("wishPrice", wishPrice)
                intent.putExtra("location", location)
                intent.putExtra("opExplanation", opExplanation)
                intent.putExtra("opHourlyRate", opHourlyRate)
                intent.putExtra("totalPayment", totalPayment)

                // 남은 근무일 수를 인텐트에 추가합니다.
                intent.putExtra("remainingWorkDays", remainingWorkDays)

                startActivity(intent)
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveData(name: String, explanation: String, price: String) {
        savedData = "물건 이름: $name\n물건 설명: $explanation\n물건 가격: $price"
    }
}
