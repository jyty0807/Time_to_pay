package com.cookandroid.timetopay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class InformationConfirm : AppCompatActivity() {

    private lateinit var icpreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information)

        icpreButton = findViewById(R.id.confirmbutton)
        icpreButton.setOnClickListener {
            // 이전으로 이동할 액티비티의 클래스를 명시
            val intent = Intent(this, Display::class.java)
            startActivity(intent)
        }
    }
}
