package com.kakao.timecat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import kotlinx.android.synthetic.main.activity_detailed_goal.*

class DetailedGoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_goal)

        val DB_NAME = "catuserdb.sql"
        val DB_VERSION = 1

        var helper = DBHelper(this, DB_NAME, DB_VERSION)
        var userId="1674815800"

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        var goalName = intent.getStringExtra("goalName").toString()

        val goals = helper.selectGoalNameData(goalName)
        goalTitle.text = goals.goal
        startTime.text = goals.startdate
        finalTime.text = goals.goaldate

        if(goals.time!="off"){
            time_alarm_layout.visibility= View.VISIBLE
            time.text=goals.time
            alarm.text=goals.alarm
        }

        goalFinish.setOnClickListener{
            mainLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.back1))
            helper.updateFinishDate(userId, goalName, "yes")
            goalFinish.visibility=View.INVISIBLE

            var intent = Intent(this, SecondActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
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
}