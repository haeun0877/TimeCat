package com.kakao.timecat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import java.util.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    val DB_NAME = "catuserdb.sql"
    val DB_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //**userId가 처음 초기화하는 값으로만 저장됨 (UserApiClient 함수안에서는 잘 작동하는데 나와선 변수안에 저장안됨**
        var userId="1674815800"
        var finish=""

        val helper = DBHelper(this, DB_NAME, DB_VERSION)
        val adapter = RecyclerAdapter()

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        val goals = helper.selectData(userId)
        adapter.listData.addAll(goals)
        adapter.setItemClickListener(object : RecyclerAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, goalname:String) {
                go_three(goalname)
            }
        })

        recyler.adapter=adapter
        recyler.layoutManager = LinearLayoutManager(this)

        //목표추가 버튼 누를시 다른 페이지로 이동
        goalAdd.setOnClickListener {
            val intent = Intent(this, GoalSettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

        var finishGoal = helper.selectFinish()


    }

    private fun go_three(goal:String) {
        var intent = Intent(this, DetailedGoalActivity::class.java)
        intent.putExtra("goalName","${goal}")
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent)
    }

}