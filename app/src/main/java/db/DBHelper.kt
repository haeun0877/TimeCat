package db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kakao.timecat.GoalSettingActivity

data class CatUser(var id:String, var name:String, var goal:String, var goaldate:String, var startdate:String, var time:String, var alarm:String, var finish:String)

class DBHelper(context: Context, name:String, version:Int)
    : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table user (id text, name text, goal text, goaldate text, startdate text, time text, alarm text, finish text)"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //테이블 변경사항이 있을경우 호출, SQLiteOpenHelper 생성자가 호출될때 기존 version과 다를 때 호출된다.
    }

    //데이터 입력 함수
    fun insertData(catUser:CatUser){
        //db 가져오기
        val wd = writableDatabase
        //CatUser을 입력타입으로 변환시켜줌
        val values = ContentValues()
        values.put("id", catUser.id)
        values.put("name", catUser.name)
        values.put("goal", catUser.goal)
        values.put("goaldate", catUser.goaldate)
        values.put("startdate", catUser.startdate)
        values.put("time", catUser.time)
        values.put("alarm", catUser.alarm)
        values.put("finish", catUser.finish)

        //db 넣기
        wd.insert("user",null,values)

        //db 닫기
        wd.close()
    }

    //데이터 조회 함수
    fun selectData(userId:String) : MutableList<CatUser>{
        val list = mutableListOf<CatUser>()

        val select = "select * from user where id = '$userId'"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)

        while(cursor.moveToNext()){
            val id = cursor.getString(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val goal = cursor.getString(cursor.getColumnIndex("goal"))
            val goaldate = cursor.getString(cursor.getColumnIndex("goaldate"))
            val startdate = cursor.getString(cursor.getColumnIndex("startdate"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val alarm = cursor.getString(cursor.getColumnIndex("alarm"))
            val finish = cursor.getString(cursor.getColumnIndex("finish"))

            val user = CatUser(id, name, goal, goaldate, startdate, time, alarm,finish)
            list.add(user)
        }
        cursor.close()
        rd.close()

        return list
    }

    //데이터 수정 함수
    fun updateData(catUser:CatUser){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("id", catUser.id)
        values.put("name", catUser.name)
        values.put("goal", catUser.goal)
        values.put("goaldate", catUser.goaldate)
        values.put("startdate", catUser.startdate)
        values.put("time", catUser.time)
        values.put("alarm", catUser.alarm)
        values.put("finish", catUser.finish)

        wd.update("user", values, "id = ${catUser.id}", null)
        wd.close()
    }

    //데이터 삭제 함수
    fun deleteData(catUser:CatUser){
        val wd = writableDatabase
        /*
        val delete = "delete from user where id = ${catUser.id}"
        wd.execSQL(delete)
         */

        wd.delete("user", "id=${catUser.id}",null)
        wd.close()
    }

    //데이터 조회 함수2
    fun selectGoalNameData(goalName:String) : CatUser {
        var user: CatUser = CatUser("","","","","","","", finish = "")

        val select = "select * from user where goal='$goalName'"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)

        while(cursor.moveToNext()){
            val id = cursor.getString(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val goal = cursor.getString(cursor.getColumnIndex("goal"))
            val goaldate = cursor.getString(cursor.getColumnIndex("goaldate"))
            val startdate = cursor.getString(cursor.getColumnIndex("startdate"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val alarm = cursor.getString(cursor.getColumnIndex("alarm"))
            val finish = cursor.getString(cursor.getColumnIndex("finish"))

            user = CatUser(id, name, goal, goaldate, startdate, time, alarm,finish)
        }
        cursor.close()
        rd.close()

        return user
    }

    //데이터 수정 함수2
    fun updateFinishDate(catUser:CatUser){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("id", catUser.id)
        values.put("goal", catUser.goal)
        values.put("finish", catUser.finish)

        wd.update("user", values, "id = ${catUser.id}", null)
        wd.close()
    }

}