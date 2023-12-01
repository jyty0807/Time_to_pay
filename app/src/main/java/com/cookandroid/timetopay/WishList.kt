package com.cookandroid.timetopay

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enterwishlist)

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

