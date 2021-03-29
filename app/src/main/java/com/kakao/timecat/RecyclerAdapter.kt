package com.kakao.timecat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import db.CatUser
import kotlinx.android.synthetic.main.goal_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<Holder>() {
    val listData = mutableListOf<CatUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.goal_item, parent, false)

        return Holder(view)
    }

    //실제로 화면에 그려주는 함수
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val catUser = listData.get(position)
        holder.setGoal(catUser)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setGoal(catUser: CatUser){
        itemView.goal_title.text="${catUser.goal}"
    }
}
