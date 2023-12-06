package com.cookandroid.timetopay

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class Alarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val week = intent.getBooleanArrayExtra("weekday")
        Log.d(TAG, week.toString())
        val cal = Calendar.getInstance()
        Log.d(TAG, cal[Calendar.DAY_OF_WEEK].toString() + "")
        if (!week!![cal[Calendar.DAY_OF_WEEK]]) return
        val format1 = SimpleDateFormat("HH:mm")
        val time = Date()
        val time1 = format1.format(time)
        val intent2 = Intent(context, AlarmTimeViewActivity::class.java)
        intent2.putExtra("setDay", cal[Calendar.DAY_OF_WEEK])
        intent2.putExtra("setTime", time1.toString())
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent2)
        Log.e("TAG", "알람입니다~!!" + "알람 요일" + cal[Calendar.DAY_OF_WEEK] + " 알람 울린 시간  : " + time1)
        Toast.makeText(context, "알람~!!", Toast.LENGTH_SHORT).show()
    } // onReceive()..

    companion object {
        var TAG = "Alarm"
    }
} // Alarm class..
