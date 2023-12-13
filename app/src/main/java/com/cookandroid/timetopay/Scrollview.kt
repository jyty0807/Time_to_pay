package com.cookandroid.timetopay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Scrollview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll)


        val button1: Button = findViewById(R.id.button1)

        button1.setOnClickListener {

            val url = "http://www.albamon.com"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)
        }


        val button2: Button = findViewById(R.id.button2)
    button2.setOnClickListener {
        // 웹사이트 URL
        val url = "http://www.alba.co.kr"

        // 인텐트 생성
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        // 브라우저 앱으로 인텐트 전달
        startActivity(intent)
    }


        val button3: Button = findViewById(R.id.button3)
        button3.setOnClickListener {
            // 웹사이트 URL
            val url = "http://www.saramin.co.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }

        val button4: Button = findViewById(R.id.button4)
        button4.setOnClickListener {
            // 웹사이트 URL
            val url =  "http://www.incruit.com"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }


        val button5: Button = findViewById(R.id.button5)
        button5.setOnClickListener {
            // 웹사이트 URL
            val url =  "http://www.youth.go.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button6: Button = findViewById(R.id.button6)
        button6.setOnClickListener {
            // 웹사이트 URL
            val url = "http://www.work.go.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button8: Button = findViewById(R.id.button8)
        button8.setOnClickListener {
            // 웹사이트 URL
            val url = "http://www.minimumwage.go.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button9: Button = findViewById(R.id.button9)
        button9.setOnClickListener {
            // 웹사이트 URL
            val url =  "http://www.youthhopefund.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button10: Button = findViewById(R.id.button10)
        button10.setOnClickListener {
            // 웹사이트 URL
            val url = "http://www.klac.or.kr"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button11: Button = findViewById(R.id.button11)
        button11.setOnClickListener {
            // 웹사이트 URL
            val url = "http://www.youthlabor.org"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }
        val button12: Button = findViewById(R.id.button12)
        button12.setOnClickListener {
            // 웹사이트 URL
            val url = "http://alba.yesform.com"

            // 인텐트 생성
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            // 브라우저 앱으로 인텐트 전달
            startActivity(intent)
        }


        }
    }