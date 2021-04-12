package com.kakao.timecat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.viewpager.widget.ViewPager
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*


class SecondActivity : AppCompatActivity() {
    val DB_NAME = "catuserdb.sql"
    val DB_VERSION = 1

    private val adapterview by lazy { ViewPagerAdapter(supportFragmentManager) }

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

        viewpager.adapter=SecondActivity@adapterview
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                indicator0_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_gray))
                indicator1_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_gray))
                indicator2_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_gray))

                when(p0){
                    0 -> indicator0_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_brown))
                    1 -> indicator1_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_brown))
                    2 -> indicator2_iv_main.setImageDrawable(getDrawable(R.drawable.shape_circle_brown))
                }
            }
        })

        //목표완수했을 때 메인화면의 버튼 색깔도 변경하는 부분
        var finishGoal = helper.selectFinish()

    }

    //액티비티 종료시 애니메이션 없애는 함수
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}