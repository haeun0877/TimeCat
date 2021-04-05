package com.kakao.timecat

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_date_picker.*
import kotlinx.android.synthetic.main.fragment_date_picker.view.*
import java.util.*

class DatePickerFragment : DialogFragment() {

    var cal = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.fragment_date_picker, container,false)
        val settingActivity = activity as GoalSettingActivity


        rootView.back.setOnClickListener{
            settingActivity.doCancel(true)
            dismiss()
        }

        rootView.ok.setOnClickListener{
            var year = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            var day = cal.get(Calendar.DATE)
            settingActivity.makeGoalTime(year,month,day)
            Log.d("caltest","${year},${month},${day}")
            dismiss()
        }

        return rootView
    }
}