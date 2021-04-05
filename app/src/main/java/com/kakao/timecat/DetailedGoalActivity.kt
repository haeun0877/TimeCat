package com.kakao.timecat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        var goalName:String

        if(intent.hasExtra("goalName")){
            goalName = intent.getStringExtra("goalName").toString()
            imsi.text="${goalName}"
        }
    }
}