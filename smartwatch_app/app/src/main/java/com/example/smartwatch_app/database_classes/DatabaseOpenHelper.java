package com.example.smartwatch_app.database_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAMES = "android_app_db.db";
    private static final String TABLE_NAME = "calendar";
    private static final int DATABASE_VERSION = 9;

    int count;


    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    String dateString = dateFormat.format(date);



    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table calendar (auto_id INTEGER PRIMARY KEY AUTOINCREMENT, emotion TEXT not null, emotion_id INTEGER not null, date DATE not null)");
    }

    private static DatabaseOpenHelper databaseInstance = null;

    public static DatabaseOpenHelper getInstance(Context context) {

        if (databaseInstance == null) {

            databaseInstance = new DatabaseOpenHelper(context);

        }

        return databaseInstance;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS calendar");
        onCreate(db);
    }

    public boolean insertData(String emotion, int emotion_id, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("emotion", emotion);
        contentValues.put("emotion_id", emotion_id);
        contentValues.put("date", date);

        long result = db.insert("calendar", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    /* ALL TIME EMOTION DATA */
    public int getAllExcitedData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 1",null);
        while (res.moveToNext()) {
            count++;
        }
        db.close();
        return count;
    }

    public int getAllHappyData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 2",null);
        while (res.moveToNext()) {
            count++;
        }
        db.close();
        return count;
    }

    public int getAllCalmData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 3",null);
        while (res.moveToNext()) {
            count++;
        }
        db.close();
        return count;
    }

    public int getAllSadData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 4",null);
        while (res.moveToNext()) {
            count++;
        }
        db.close();
        return count;
    }

    public int getAllAngryData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 5",null);
        while (res.moveToNext()) {
            count++;
        }
        db.close();
        return count;
    }

    /* TODAY emotion_id DATA */
    public int getTodayExcitedData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 1",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(0,2);
            String nowDate = dateString.substring(0, 2);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getTodayHappyData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 2",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(0,2);
            String nowDate = dateString.substring(0, 2);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getTodayCalmData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 3",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(0,2);
            String nowDate = dateString.substring(0, 2);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getTodaySadData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 4",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(0,2);
            String nowDate = dateString.substring(0, 2);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getTodayAngryData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 5",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(0,2);
            String nowDate = dateString.substring(0, 2);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    /* MONTH emotion_id DATA */
    public int getMonthExcitedData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 1",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(3,5);
            String nowDate = dateString.substring(3, 5);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getMonthHappyData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 2",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(3,5);
            String nowDate = dateString.substring(3, 5);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getMonthCalmData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 3",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(3,5);
            String nowDate = dateString.substring(3, 5);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }


    public int getMonthSadData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 4",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(3,5);
            String nowDate = dateString.substring(3, 5);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    public int getMonthAngryData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = '5'",null);
        while (res.moveToNext()) {
            String date_db = res.getString(3);
            String getDate = date_db.substring(3,5);
            String nowDate = dateString.substring(3, 5);

            if (getDate.equals(nowDate)){
                count++;
            }

        }
        db.close();
        return count;
    }

    /* WEEK emotion_id DATA */
    public int getWeekExcitedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 1", null);
        while (res.moveToNext()) {

            String date_db = res.getString(3);

            for (int i = 0; i >= -7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);

                String nowDate = fromdate.substring(0, 2);
                String dateDay = date_db.substring(0, 2);

                if (nowDate.equals(dateDay)) {
                    count++;
                }
            }
        }
        db.close();
        return count;
    }

    public int getWeekHappyData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 2", null);
        while (res.moveToNext()) {

            String date_db = res.getString(3);

            for (int i = 0; i >= -7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);

                String nowDate = fromdate.substring(0, 2);
                String dateDay = date_db.substring(0, 2);


                if (nowDate.equals(dateDay)) {
                    count++;
                }
            }
        }
        db.close();
        return count;
    }

    public int getWeekCalmData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 3", null);
        while (res.moveToNext()) {

            String date_db = res.getString(3);

            for (int i = 0; i >= -7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);

                String nowDate = fromdate.substring(0, 2);
                String dateDay = date_db.substring(0, 2);


                if (nowDate.equals(dateDay)) {
                    count++;
                }
            }
        }
        db.close();
        return count;
    }

    public int getWeekSadData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 4", null);
        while (res.moveToNext()) {

            String date_db = res.getString(3);

            for (int i = 0; i >= -7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);

                String nowDate = fromdate.substring(0, 2);
                String dateDay = date_db.substring(0, 2);

                if (nowDate.equals(dateDay)) {
                    count++;
                }
            }
        }
        db.close();
        return count;
    }

    public int getWeekAngryData() {
        count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM calendar WHERE emotion_id = 5", null);
        while (res.moveToNext()) {

            String date_db = res.getString(3);

            for (int i = 0; i >= -7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                Date todate1 = cal.getTime();
                String fromdate = dateFormat.format(todate1);

                String nowDate = fromdate.substring(0, 2);
                String dateDay = date_db.substring(0, 2);

                if (nowDate.equals(dateDay)) {
                    count++;
                }
            }
        }
        db.close();
        return count;
    }

    public ArrayList<EventObjects> getAllFutureEvents(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<EventObjects> events = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM calendar", null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(1);
                int emotion_id = cursor.getInt(2);
                String date_string = cursor.getString(3);


                Date date = convertStringToDate(date_string);
                long millis = date.getTime();


                // add eventobject to arraylist
                events.add(new EventObjects(id, message, emotion_id, millis));

            }
            while (cursor.moveToNext());

        }
        cursor.close();
        return events;
    }


    public Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }


}

