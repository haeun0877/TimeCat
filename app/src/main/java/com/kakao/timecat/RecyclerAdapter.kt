package com.kakao.timecat

import android.content.Intent
import android.os.Build
import android.system.Os.bind
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import db.CatUser
import kotlinx.android.synthetic.main.goal_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val listData = mutableListOf<CatUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.goal_item, parent, false)

        return ViewHolder(view)
    }

    //실제로 화면에 그려주는 함수
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val catUser = listData.get(position)
        holder.setGoal(catUser)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, catUser.goal)
        }
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, goalName: String)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setGoal(catUser: CatUser){
            itemView.goal_title.text="${catUser.goal}"
        }

    }
}

