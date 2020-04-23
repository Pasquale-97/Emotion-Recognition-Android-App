package com.example.smartwatch_app.database_classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
public class DatabaseObject {
    private static DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;


    public DatabaseObject(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
        this.dbHelper.getWritableDatabase();
//      this.db = dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getDbConnection(){
        return this.db;
    }

    public void closeDbConnection(){
        if (this.db != null) {
            this.db.close();
        }
    }
}