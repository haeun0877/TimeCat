package com.kakao.timecat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_goal_setting.*

class GoalSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        switchDate.setChecked(true)
        switchDate.setOnCheckedChangeListener{_, onSwitch ->
            if(onSwitch){
                switchDate.text="select"
                dataPick.visibility= View.VISIBLE
            }
            else{
                switchDate.text="always"
                dataPick.visibility= View.INVISIBLE
            }
        }
    }
}