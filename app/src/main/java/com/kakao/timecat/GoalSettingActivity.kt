package com.kakao.timecat

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.kakao.sdk.user.UserApiClient
import db.CatUser
import db.DBHelper
import kotlinx.android.synthetic.main.activity_goal_setting.*
import java.time.LocalDate
import java.util.*

//goalTime 에러 -> 아무런 값도 안들어가고있음 !! 수정 필요 !!

class GoalSettingActivity : AppCompatActivity() {
    var goalTime:String = "always"
    var time:String="off"
    var finish:String="no"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        val DB_NAME = "catdb.sql"
        val DB_VERSION = 1
        var userId:String = ""
        var userNickname:String = ""
        var startTime:String = ""
        var alarmOn:String="off"

        //목표날짜 always, select중 고르는 스위치버튼
        switchDate.setOnCheckedChangeListener{_, onSwitch ->
            if(onSwitch) {
                switchDate.text = "select"
                showDatePicker()
            }
            else{
                goalTimeText.text=""
                switchDate.text="always"
            }
        }

        //목표시간 off, on중 고르는 스위치버튼
        switchOnOff.setOnCheckedChangeListener { _, isOn ->
            if(isOn){
                switchOnOff.text="on"
                alamOnOff.visibility=View.VISIBLE
                showTimePicker()
            }else{
                switchOnOff.text="off"
                alamOnOff.visibility=View.INVISIBLE
                resulttime.visibility=View.INVISIBLE
            }
        }

        //알람 on, onff중 고르는 스위치 버튼
        alamOnOff.setOnCheckedChangeListener {_, isOn->
            if(isOn){
                alarmOn="On"
                alamOnOff.text="알람on"
            }else{
                alarmOn="Off"
                alamOnOff.text="알람off"
            }
        }


        var nowTime : LocalDate = LocalDate.now()
        startTime = nowTime.toString()

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
            userNickname = user?.kakaoAccount?.profile?.nickname.toString()
        }

        //카카오 아이디,이름 불러오기 잘 작동됨
        //Toast.makeText(this, "${userId},${userNickname}", Toast.LENGTH_SHORT).show()

        var year1:Int=0

        goalAdd.setOnClickListener {
            if (!switchDate.isChecked)
                goalTime = "always"

            if (goalName.length()<=0) {
                Toast.makeText(this, "목표제목을 설정해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                var helper = DBHelper(this, DB_NAME, DB_VERSION)
                var catUser = CatUser(userId, userNickname, goalName.text.toString(), goalTime, startTime, time, alarmOn, finish)
                helper.insertData(catUser)

                var intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }


    }

    fun doCancel(cancel:Boolean){
        if(cancel){
            switchDate.isChecked=false
        }
    }

    fun doCancelTime(cancel: Boolean){
        if(cancel){
            switchOnOff.isChecked=false
            resulttime.visibility=View.INVISIBLE
        }
    }

    //목표날짜 설정하는 함수
    fun makeGoalTime(year:Int, month:Int, day:Int){
        goalTime = "${year}-${month}-${day}"
        goalTimeText.text="${goalTime}"
    }

    //목표시간을 설정하는 함수
    fun makeTimeText(hour:Int, minute:Int){
        time = "${hour}:${minute}"
        resulttime.visibility=View.VISIBLE
        resulttime.text="${time} 시작"
    }

    //데이트피커다이어로그 생성함수
    private fun showDatePicker() {
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, dat ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DATE, dat)

            makeGoalTime(year, month+1, dat)
        }

        DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)). show()
    }

    //타임피커다이어로그 생성함수
    private fun showTimePicker() {
        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            makeTimeText(hour, minute)
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

}