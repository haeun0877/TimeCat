package com.kakao.timecat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import db.CatUser
import db.DBHelper
import kotlinx.android.synthetic.main.activity_detailed_goal.*

class DetailedGoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_goal)

        val DB_NAME = "catdb.sql"
        val DB_VERSION = 1

        var helper = DBHelper(this, DB_NAME, DB_VERSION)
        var goalName:String=""

        if(intent.hasExtra("goalName")){
            goalName = intent.getStringExtra("goalName").toString()
        }

        val goals = helper.selectGoalNameData(goalName)
        goalTitle.text = goals.goal
        startTime.text = goals.startdate
        finalTime.text = goals.goaldate

        if(goals.time!="off"){
            time_alarm_layout.visibility= View.VISIBLE
            time.text=goals.time
            alarm.text=goals.alarm
        }
    }
}