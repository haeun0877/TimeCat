package com.kakao.timecat

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        val DB_NAME = "catDB.sql"
        val DB_VERSION = 1
        var userId:String = ""
        var userNickname:String = ""
        var startTime:String = ""
        val dialogFragment = DatePickerFragment()
        val dialogFragmentTime = TimePickerFragment()

        switchDate.setOnCheckedChangeListener{_, onSwitch ->
            if(onSwitch) {
                switchDate.text = "select"
                dialogFragment.show(supportFragmentManager,"customDialog")
            }
            else{
                goalTimeText.text=""
                switchDate.text="always"
            }
        }

        switchOnOff.setOnCheckedChangeListener { _, isOn ->
            if(isOn){
                switchOnOff.text="on"
                alamOnOff.visibility=View.VISIBLE
                dialogFragmentTime.show(supportFragmentManager,"timeCustomDialog")
            }else{
                switchOnOff.text="off"
                alamOnOff.visibility=View.INVISIBLE
                resulttime.visibility=View.INVISIBLE
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
        goalAdd.setOnClickListener{
            if(!switchDate.isChecked)
                goalTime="always"

            /*
            var helper = DBHelper(this, DB_NAME, DB_VERSION)
            var catUser = CatUser(userId,userNickname,goalName.text.toString(),goalTime,startTime,time)
            helper.insertData(catUser)

            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)

             */
            Toast.makeText(this, "${goalTime}", Toast.LENGTH_SHORT).show()
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

    fun makeGoalTime(year:Int, month:Int, day:Int){
        goalTime = "${year}/${month}/${day}"
        goalTimeText.text="${goalTime}"
    }

    fun makeTimeText(hour:Int, minute:Int, ampm:String){
        time = "${hour}:${minute} ${ampm}"
        resulttime.visibility=View.VISIBLE
        resulttime.text="${time}"
    }
}