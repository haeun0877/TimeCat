package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class CatUser(var id:String, var name:String, var goal:String, var goaldate:String, var startdate:String)

class DBHelper(context: Context, name:String, version:Int)
    : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table user (id text primary key, name text, goal text, goaldate text, startdate text)"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //테이블 변경사항이 있을경우 호출, SQLiteOpenHelper 생성자가 호출될때 기존 version과 다를 때 호출된다.
    }

    //데이터 입력 함수
    fun insertData(catUser:CatUser){

    }
}