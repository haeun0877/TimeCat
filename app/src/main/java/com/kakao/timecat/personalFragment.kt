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
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.fragment_personal.*

class personalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserApiClient.instance.me { user, error ->
            if(user?.kakaoAccount?.profile?.profileImageUrl!=null){
                Glide.with(this).load(user?.kakaoAccount?.profile?.profileImageUrl).into(imageView)
            }else{
                Glide.with(this).load(R.drawable.cat3).into(imageView)
            }

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

        week_goal.text=""
        month_goal.text=""
    }

}