package com.cookandroid.timetopay

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
    private lateinit var nextt_button: Button
    private lateinit var wpreviousButton: Button

    private var savedData: String = ""  // 추출한 데이터를 저장할 변수

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

        // EditText들을 참조
        wishNameEditText = findViewById(R.id.wishNameEditText)
        wishExplainEditText = findViewById(R.id.wishExplainEditText)
        wishPriceEditText = findViewById(R.id.wishPrice)

        wishlistButton = findViewById(R.id.wishlistbutton)
        wishlistButton.setOnClickListener {
            // 버튼이 클릭되었을 때 EditText에서 데이터 추출
            val wishName = wishNameEditText.text.toString()
            val wishExplain = wishExplainEditText.text.toString()
            val wishPrice = wishPriceEditText.text.toString()

            if (wishName.isEmpty() || wishExplain.isEmpty() || wishPrice.isEmpty()) {
                showToast("모든 항목을 입력하세요.")
            } else {
                // 데이터가 비어있지 않다면 저장하고 사용자에게 보여줌
                saveData(wishName, wishExplain, wishPrice)
                showToast("물건 이름: $wishName\n물건 설명: $wishExplain\n물건 가격: $wishPrice")

                val intent = Intent(this, Display::class.java)
                intent.putExtra("wishName", wishName)  // 데이터를 "wishName"이라는 키로 전달
                intent.putExtra("wishExplain", wishExplain)  // 데이터를 "wishExplain"이라는 키로 전달
                intent.putExtra("wishPrice", wishPrice)  // 데이터를 "wishPrice"이라는 키로 전달

                // 새로운 액티비티 시작
                startActivity(intent)
            }
        }

        // nextt_button과 setOnClickListener가 wishlistButton의 블록 바깥에 위치하도록 변경
        nextt_button = findViewById(R.id.nexttbutton)
        nextt_button.setOnClickListener {
            // 다음으로 이동할 액티비티의 클래스를 명시
            val intent = Intent(this, informationConfirm::class.java)
            startActivity(intent)
        }

        // wpreviousButton과 setOnClickListener가 wishlistButton의 블록 바깥에 위치하도록 변경
        wpreviousButton = findViewById(R.id.w_Pre_button)
        wpreviousButton.setOnClickListener {
            // 이전으로 이동할 액티비티의 클래스를 명시
            val intent = Intent(this, InfoInput::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        // 추출한 데이터를 토스트 메시지로 보여주는 함수
        // (실제 앱에서는 더 나은 방식으로 데이터를 활용할 것)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveData(name: String, explanation: String, price: String) {
        // 추출한 데이터를 문자열로 저장하는 함수
        savedData = "물건 이름: $name\n물건 설명: $explanation\n물건 가격: $price"
    }
}









