package com.example.mad_21012011020_practical_8

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val card=findViewById<MaterialCardView>(R.id.card)

        val alarmbutton=findViewById<MaterialButton>(R.id.clock)
        alarmbutton.setOnClickListener {
            //card.visibility=View.VISIBLE
            TimePickerDialog(this,{tp,hour,minute->setalarmtime(hour, minute)},Calendar.HOUR,Calendar.MINUTE,false).show()
        }

    }
    fun stop(){
        setalarm(-1,AlarmBroadcastReceiver.Alarmstop)
    }
    fun setalarmtime(hour: Int, minute: Int){
        //card.visibility=View.GONE
        val alarmtime=Calendar.getInstance()
        val year=alarmtime.get(Calendar.YEAR)
        val month=alarmtime.get(Calendar.MONTH)
        val date=alarmtime.get(Calendar.DATE)
        alarmtime.set(year,month, date,hour,minute,0)
        setalarm(alarmtime.timeInMillis,AlarmBroadcastReceiver.Alarmstart)
    }
    fun setalarm(militime:Long,action:String){
        val intentalarm=Intent(applicationContext,AlarmBroadcastReceiver::class.java)
        intentalarm.putExtra(AlarmBroadcastReceiver.Alarmkey,action)
        val pendingIntent=PendingIntent.getBroadcast(applicationContext,4356,intentalarm,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT )
        val alarmmanager=getSystemService(ALARM_SERVICE) as AlarmManager
        if (action==AlarmBroadcastReceiver.Alarmstart){
            alarmmanager.setExact(AlarmManager.RTC_WAKEUP,militime,pendingIntent)
        }
        else if (action==AlarmBroadcastReceiver.Alarmstop){
            alarmmanager.cancel(pendingIntent)
            sendBroadcast(intentalarm)
        }
    }
}