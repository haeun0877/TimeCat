package com.kakao.timecat

import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.EventDay
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.lang.reflect.Array.set
import java.time.LocalDate
import java.util.*


class calendarFragment : Fragment() {
    val DB_NAME = "catuserdb2.sql"
    val DB_VERSION = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var nowDate = LocalDate.now().toString()
        date_view.text="${nowDate}"

        val helper = DBHelper(requireContext(), DB_NAME, DB_VERSION)
        var userId=""

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        val calendar = Calendar.getInstance()
        var event = EventDay(calendar, R.drawable.catt)
        val events: ArrayList<EventDay> = arrayListOf(event)
        val calendarArr = helper.selectDay(userId)

        for(i in 0..calendarArr.size-1){
            events.add(EventDay(calendarArr[i],  R.drawable.catt))
        }
        calendarView.setEvents(events)
        /*
        var size = helper.selectFinish().size

        if (size != null) {
            if(size>0){
                val calendar = Calendar.getInstance()
                var event = EventDay(calendar, R.drawable.catt)
                val events: ArrayList<EventDay> = arrayListOf(event)
                var calendarArr:ArrayList<Calendar> = ArrayList<Calendar>()
                calendarArr.add(Calendar.getInstance())
                calendarArr[0].set(2021,3,7)
                calendarArr.add(Calendar.getInstance())
                calendarArr[1].set(2021,3,18)
                events.add(EventDay(calendarArr[0],  R.drawable.catt))
                events.add(EventDay(calendarArr[1],  R.drawable.catt))
                calendarView.setEvents(events)

            }
        }

         */



    }

}

