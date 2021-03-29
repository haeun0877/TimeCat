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

class GoalSettingActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        val DB_NAME = "cattime.sql"
        val DB_VERSION = 1
        var userId:String = ""
        var userNickname:String = ""
        var goal:String=""
        var goalTime:String=""
        var startTime:String=""

        switchDate.setChecked(true)
        switchDate.setOnCheckedChangeListener{_, onSwitch ->
            if(onSwitch) {
                switchDate.text = "select"
                dataPick.visibility = View.VISIBLE

                var date_listener = object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                        goalTime = "${year}-${month}-${day}"
                    }
                }
            }
            else{
                switchDate.text="always"
                dataPick.visibility= View.INVISIBLE
                goalTime="always"
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

        goalAdd.setOnClickListener{
            goal= goalName.text.toString()

            val helper = DBHelper(this, DB_NAME, DB_VERSION)
            val catUser = CatUser(userId,userNickname,goal,goalTime,startTime)
            helper.insertData(catUser)

            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}