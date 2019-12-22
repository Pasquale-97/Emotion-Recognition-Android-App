package com.example.smartwatch_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static final String TAG = "MyActivity";

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT sysBP, diaBP, heartRate  FROM heart_readings_sim ORDER BY ROWID DESC LIMIT 1", null);
        cursor.moveToFirst();
        String sys_bp = "";
        String dis_bp = "";
        String heartRate = "";


        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            list.add(cursor.getString(1));
            list.add(cursor.getString(2));

            sys_bp = cursor.getString(0);
            dis_bp = cursor.getString(1);
            heartRate = cursor.getString(2);

            cursor.moveToNext();
        }

        Log.d(TAG, "Sys BP: " + sys_bp);
        Log.d(TAG, "Dis BP: " + dis_bp);
        Log.d(TAG, "Heart Rate: " + heartRate);

        cursor.close();
        return list;
    }
}
