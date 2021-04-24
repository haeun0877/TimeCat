package com.kakao.timecat

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import kotlinx.android.synthetic.main.activity_detailed_goal.*
import kotlinx.android.synthetic.main.activity_goal_setting.*
import java.util.*

class DetailedGoalActivity : AppCompatActivity() {
    val DB_NAME = "catuserdb2.sql"
    val DB_VERSION = 1

    var helper = DBHelper(this, DB_NAME, DB_VERSION)
    var userId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_goal)

        var updateClick = 0

        var intent_second = Intent(this, SecondActivity::class.java)
        intent_second.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        var goalName = intent.getStringExtra("goalName").toString()

        val goals = helper.selectGoalNameData(goalName)
        goalTitle.text = goals.goal
        startTime.text = goals.startdate
        finalTime.text = goals.goaldate
        time.text=goals.time

        if(goals.time!="off"){
            time_alarm_layout.visibility= View.VISIBLE
        }

        goalFinish.setOnClickListener{
            if(helper.selectFinish().size<1){
                val calendar:Calendar = Calendar.getInstance()
                helper.insertFinishDay(userId, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            }

            mainLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.back1))
            helper.updateFinishDate(userId, goalName, "yes")
            goalFinish.visibility=View.INVISIBLE

            startActivity(intent_second)
        }

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        goalupdate.setOnClickListener{
            updateClick+=1

            if(updateClick<2){
                dateupdate.visibility=View.VISIBLE
                timeupdate.visibility=View.VISIBLE
                goalFinish.visibility=View.INVISIBLE
                delete_button.visibility=View.VISIBLE
                time_alarm_layout.visibility=View.VISIBLE

                dateupdate.setOnClickListener{
                    showDatePicker()
                }
                timeupdate.setOnClickListener{
                    showTimePicker()
                }

                delete_button.setOnClickListener{
                    makeAlertDialog()
                }
            }else{
                updateClick=0
                helper.updateGoal(userId, goalTitle.text.toString(), goalFinish.text.toString(), time.text.toString())
                startActivity(intent_second)
            }
        }


        if(goals.finish=="yes"){
            mainLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.back1))
            goalFinish.visibility=View.INVISIBLE
        }else{
            mainLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.back2))
            goalFinish.visibility=View.VISIBLE
        }
    }

    //액티비티 종료시 애니메이션 없애는 함수
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
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

    //목표날짜 설정하는 함수
    private fun makeGoalTime(year:Int, month:Int, day:Int){
        var goalTime = "${year}-${month}-${day}"
        finalTime.text="${goalTime}"
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

    //목표시간을 설정하는 함수
    private fun makeTimeText(hour:Int, minute:Int){
        var timetext = "${hour}:${minute}"
        time.text="${timetext}"
    }

    private fun makeAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("'${goalTitle.text.toString()}'를 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
            helper.deleteGoal(userId,goalTitle.text.toString())
            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            var intent_second = Intent(this, SecondActivity::class.java)
            startActivity(intent_second)
        }
        builder.setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->
        }
        builder.show()
    }

}