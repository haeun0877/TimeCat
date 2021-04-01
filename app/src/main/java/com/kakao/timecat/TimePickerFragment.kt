package com.kakao.timecat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_date_picker.view.*
import java.util.*

class TimePickerFragment : DialogFragment() {

    var cal = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var rootView: View = inflater.inflate(R.layout.fragment_time_picker, container,false)
        val settingActivity = activity as GoalSettingActivity

        var ampm:String=""

        rootView.back.setOnClickListener{
            settingActivity.doCancelTime(true)
            dismiss()
        }

        rootView.ok.setOnClickListener{
            val hour = cal.get(Calendar.HOUR)
            val minute = cal.get(Calendar.MINUTE)
            if (cal.get(Calendar.AM_PM) == Calendar.AM)
                ampm = "AM";
            else if (cal.get(Calendar.AM_PM) == Calendar.PM)
                ampm = "PM";

            settingActivity.makeTimeText(hour,minute,ampm)
            dismiss()
        }

        return rootView
    }
}