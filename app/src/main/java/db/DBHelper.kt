package db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList

data class CatUser(var id:String, var name:String, var goal:String, var goaldate:String, var startdate:String, var time:String, var finish:String)

class DBHelper(context: Context, name:String, version:Int)
    : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table user (id text, name text, goal text, goaldate text, startdate text, time text, finish text)"
        val createdate = "create table calendar (today text)"
        val createfinish = "create table finishday (id text, year text, month text, day text)"
        db?.execSQL(create)
        db?.execSQL(createdate)
        db?.execSQL(createfinish)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //테이블 변경사항이 있을경우 호출, SQLiteOpenHelper 생성자가 호출될때 기존 version과 다를 때 호출된다.
    }

    //데이터 입력 함수
    fun insertData(catUser: com.kakao.timecat.CatUser){
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
        values.put("finish", catUser.finish)

        //db 넣기
        wd.insert("user",null,values)

        //db 닫기
        wd.close()
    }

    //오늘날짜입력
    fun insertDay(today:String){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("today", today)
        wd.insert("calendar",null,values)
        wd.close()
    }

    //이미 저장된 day가 있는지 비교하는 함수
    fun dayExistOrNot() : Boolean{
        var dat_exist = false
        var num=0
        val select = "select * from calendar"
        val rd =readableDatabase
        val cursor = rd.rawQuery(select,null)

        while(cursor.moveToNext()){
            if(cursor.columnCount==num){
                dat_exist=false
            }else{
                dat_exist=true
            }
        }
        return dat_exist
    }

    //date가 이미 존재하면 오늘 날짜로 update하는 함수
    fun updateDay(day:String){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("today",day)
        wd.update("calendar", values, "", null)
        wd.close()
    }

    //테이블에 저장되어있는 오늘 날짜를 불러오는함수
    fun selectDay() : String{
        val select = "select * from calendar"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)
        var date = ""
        while(cursor.moveToNext()) {
                date = cursor.getString(cursor.getColumnIndex("today"))
        }
        cursor.close()
        rd.close()
        return date
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
            val finish = cursor.getString(cursor.getColumnIndex("finish"))

            val user = CatUser(id, name, goal, goaldate, startdate, time, finish)
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

    //목표이름으로 데이터 찾는 함수
    fun selectGoalNameData(goalName:String) : CatUser {
        var user: CatUser = CatUser("","","","","","","")

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
            val finish = cursor.getString(cursor.getColumnIndex("finish"))

            user = CatUser(id, name, goal, goaldate, startdate, time,finish)
        }
        cursor.close()
        rd.close()

        return user
    }

    //끝난 목표 정보를 수정하는 함수 (ex: finish)
    fun updateFinishDate(id:String, goal:String, finish:String){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("goal", goal)
        values.put("id", id)
        values.put("finish", finish)

        wd.update("user", values, "goal='$goal' and id='$id'", null)
        wd.close()
    }

    //목표 수정 버튼을 눌렀을 때 목표 수정을 수행하는 함수
    fun updateGoal(id:String, goal:String, goaldate:String, time:String){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("goal", goal)
        values.put("id", id)
        values.put("goaldate", goaldate)
        values.put("time", time)

        wd.update("user", values, "goal='$goal' and id='$id'", null)
        wd.close()
    }

    //달성한 목표들만 불러와서 리스트로 저장하는 함수ArrayList<String>
    fun selectFinish() :ArrayList<String>{
        val arrayList = ArrayList<String>()
        val select = "select * from user where finish= 'yes'"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)

        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("goal")))
        }
        cursor.close()
        rd.close()

        return arrayList
    }

    //사용자의 목표를 삭제하는 함수
    fun deleteGoal(id:String, goal:String){
        val wd = writableDatabase
        wd.delete("user", "id='$id' and goal='$goal'",null)
        wd.close()
    }

    //컬럼이 몇번째에 있는지 int로 반환하는 함수
    fun selectNum(goal:String) : Int{
        val select = "select * from user"
        val rd =readableDatabase
        val cursor = rd.rawQuery(select,null)
        var num=0

        while(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex("goal"))==goal)
                num=cursor.position
        }

        cursor.close()
        rd.close()

        return num
    }

    //매일마다 수행된 목표들을 초기화
    fun changeNotFinish(){
        val wd = writableDatabase
        val values = ContentValues()
        values.put("finish", "no")

        wd.update("user", values, "" , null)
        wd.close()
    }

    //목표를 완수한 일을 저장하는 함수
    fun insertFinishDay(userId:String ,year:Int, month:Int, day:Int){
        //db 가져오기
        val wd = writableDatabase
        //CatUser을 입력타입으로 변환시켜줌
        val values = ContentValues()
        values.put("id", userId)
        values.put("year", year)
        values.put("month", month)
        values.put("day", day)
        //db 넣기
        wd.insert("finishday",null,values)
        //db 닫기
        wd.close()
    }

    //끝낸날 조회 함수
    fun selectDay(userId:String) : ArrayList<Calendar>{
        val calendarArr:ArrayList<Calendar> = ArrayList<Calendar>()

        val select = "select * from finishday where id = '$userId'"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)
        var num=0

        while(cursor.moveToNext()){
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))

            calendarArr.add(Calendar.getInstance())
            calendarArr[num].set(year,month,day)
            num+=1
        }
        cursor.close()
        rd.close()

        return calendarArr
    }
}