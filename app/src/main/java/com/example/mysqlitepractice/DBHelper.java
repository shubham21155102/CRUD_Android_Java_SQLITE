package com.example.mysqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String ID = "id";

    public DBHelper(@Nullable Context context) {
        super(context,"shubhamtest",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table users(" + ID + " integer primary key autoincrement," + NAME + " text," + EMAIL + " text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        long insert = db.insert("users", null, values);
        db.close();
        if (insert == -1) {
            return false;
        }
        return true;
    }
    public Cursor getRecord(String name){
       SQLiteDatabase db=this.getReadableDatabase();
         Cursor cursor=db.rawQuery("select * from users where name=?",new String[]{name});
            return cursor;
    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from users",null);
        return cursor;
    }
    public boolean updateData(String id,String name,String email){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("email",email);
        long update=db.update("users",values,"id=?",new String[]{id});
        if(update==-1){
            return false;
        }
        return true;
    }
    public boolean deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete("users","id=?",new String[]{id});
        if(delete==-1){
            return false;
        }
        return true;
    }
    public boolean deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete("users",null,null);
        if(delete==-1){
            return false;
        }
        return true;
    }

}
