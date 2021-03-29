package com.kakao.timecat

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import db.CatUser
import db.DBHelper
import kotlinx.android.synthetic.main.activity_goal_setting.*

class GoalSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        val DB_NAME = "cattime.sql"
        val DB_VERSION = 1
        var userId:String = ""

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

        UserApiClient.instance.me { user, error ->
            userId = user?.id.toString()
        }

        goalAdd.setOnClickListener{
            Toast.makeText(this, "${userId}", Toast.LENGTH_SHORT).show()
            /*
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)

             */
        }

        /*
        val helper = DBHelper(this, DB_NAME, DB_VERSION)

        val catUser = CatUser()
         */



    }
}