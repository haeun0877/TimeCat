package com.kakao.timecat

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat.from
import com.applandeo.materialcalendarview.EventDay
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import db.DBHelper
import kotlinx.android.synthetic.main.fragment_personal.*
import java.util.*

class personalFragment : Fragment() {
    val DB_NAME = "catuserdb0.sql"
    val DB_VERSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events: ArrayList<EventDay> = ArrayList<EventDay>()
        var calendarArr = ArrayList<Calendar>()

        val helper = DBHelper(requireContext(), DB_NAME, DB_VERSION)

        UserApiClient.instance.me { user, error ->
            if(user?.kakaoAccount?.profile?.profileImageUrl!=null){
                Glide.with(this).load(user?.kakaoAccount?.profile?.profileImageUrl).into(imageView)
            }else{
                Glide.with(this).load(R.drawable.cat3).into(imageView)
            }

            var userId = user?.id.toString()
            calendarArr = helper.selectFinishDay(userId)

            sticker_num.text="${calendarArr.size}개"
            nickname.text = user?.kakaoAccount?.profile?.nickname +"님"
        }

        logout.setOnClickListener{
            UserApiClient.instance.logout { error ->
                if (error != null)
                    Toast.makeText(this.context, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                val intent = Intent(this.activity, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
    }

}