package com.example.user.hw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by User on 28.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "persons";
    private static final String TABLE_NAME = "person_info";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "SURNAME";
    private static final String COL_4 = "NUMBER";
    private static final String COL_5 = "EMAIL";
    private static final String COL_6 = "PASSWORD";
    private ContentValues contentValues;
    private SQLiteDatabase sql;
    private Cursor cursor;

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table "+ TABLE_NAME +" ( "+ COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_2
                + " TEXT, "+ COL_3 +" TEXT, "+ COL_4 + " TEXT, "+ COL_5 + " TEXT, " + COL_6 + " TEXT ); ");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public boolean insertData(Context context, String name,String surname, String number, String email, String password) {
        sql = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, number);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, password);
        long result = sql.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context,R.string.Error,Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData()
    {
        sql = this.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public Cursor getEmailList()
    {
        sql = this.getWritableDatabase();
        cursor = sql.rawQuery(" SELECT " + COL_5 + " FROM " + TABLE_NAME,null);
        return cursor;
    }

    public Cursor getUser(String email, String password)
    {
        sql = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_5 + "=? AND " + COL_6 + "=?";
        cursor = sql.rawQuery(query,new String[] {email,password},null);
        return cursor;
    }

    public String getId(String email)
    {
        sql = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_5 + "=?;";
        Cursor cursor = sql.rawQuery(query,new String[] {email},null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public boolean updateData(String id, String phone, String email) {
        sql = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,phone);
        contentValues.put(COL_5,email);
        sql.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
}