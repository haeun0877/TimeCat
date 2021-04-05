package com.kakao.timecat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import db.CatUser
import db.DBHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    val DB_NAME = "catdb.sql"
    val DB_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val helper = DBHelper(this, DB_NAME, DB_VERSION)
        val adapter = RecyclerAdapter()

        val goals = helper.selectData()
        adapter.listData.addAll(goals)
        adapter.setItemClickListener(object : RecyclerAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, goalname:String) {
                go_three(goalname)
            }
        })

        recyler.adapter=adapter
        recyler.layoutManager = LinearLayoutManager(this)


        goalAdd.setOnClickListener{
            val intent = Intent(this, GoalSettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun go_three(goal:String) {
        var intent = Intent(this, DetailedGoalActivity::class.java)
        intent.putExtra("goalName","${goal}")
        startActivity(intent)
    }

}