package com.example.semester_project_health_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String qry1="create table users(username text,email text,password text)";
db.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register(String username,String email,String password){
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase dbi=getWritableDatabase();
        dbi.insert("users",null,cv);
        dbi.close();
    }
    public int login(String username,String password){
        int result=0;
        String[] arr=new String[2];
        arr[0]=username ;
        arr[1]=password;
        SQLiteDatabase dbs=getReadableDatabase();
        Cursor c= dbs.rawQuery("select * from users where username=? and password=?",arr);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }
}
