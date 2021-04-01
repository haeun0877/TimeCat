package com.kakao.timecat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import db.DBHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    val DB_NAME = "catDB.sql"
    val DB_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val helper = DBHelper(this, DB_NAME, DB_VERSION)
        val adapter = RecyclerAdapter()

        val goals = helper.selectData()
        adapter.listData.addAll(goals)

        adapter.setItemClickListener(object : RecyclerAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Log.d("clicktest","야호ㅠㅠㅠ")
            }
        })

        recyler.adapter=adapter
        recyler.layoutManager = LinearLayoutManager(this)


        goalAdd.setOnClickListener{
            val intent = Intent(this, GoalSettingActivity::class.java)
            startActivity(intent)
        }


    }

}