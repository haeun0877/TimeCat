package com.kakao.timecat

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.kakao.sdk.user.UserApiClient
import db.CatUser
import db.DBHelper
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_recycler.*
import java.time.LocalDate

data class CatUser(var id:String, var name:String, var goal:String, var goaldate:String, var startdate:String, var time:String, var alarm:String, var finish:String)

class recyclerFragment : Fragment() {
    val DB_NAME = "catuserdb0.sql"
    val DB_VERSION = 1
    val activityContext: Activity by lazy { activity as SecondActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val helper = DBHelper(requireContext(), DB_NAME, DB_VERSION)
        val adapter = RecyclerAdapter()
        val activityS = SecondActivity()

        var today = ""

        var userId=""
        var finish=""
        var goals : MutableList<CatUser>

        val activityContext: Activity by lazy { activity as SecondActivity}

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
            goals = helper.selectData(userId)
            adapter.listData.addAll(goals)

            adapter.setItemClickListener(object : RecyclerAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int, goalname:String) {
                    go_three(goalname)
                }
            })

            recyler.adapter=adapter
            recyler.layoutManager = LinearLayoutManager(requireContext())

            helper.deleteFinishDate(userId)

            //calendar테이블에 day가 저장되어있는지 확인하고 오늘 날짜로 date를 update하는부분
            if(!helper.dayExistOrNot(userId)){
                helper.insertDay(userId,LocalDate.now().toString())
            }
            else{
                today = helper.selectDay(userId)
                if(today!=LocalDate.now().toString()){
                    helper.updateDay(userId,LocalDate.now().toString())
                    helper.changeNotFinish()
                }
                helper.updateDay(userId,LocalDate.now().toString())
            }
        }

        //목표추가 버튼 누를시 다른 페이지로 이동
        goalAdd.setOnClickListener {
            val intent = Intent(this.activityContext, GoalSettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun go_three(goal:String) {
        var intent = Intent(this.activityContext, DetailedGoalActivity::class.java)
        intent.putExtra("goalName","${goal}")
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent)

    }
}