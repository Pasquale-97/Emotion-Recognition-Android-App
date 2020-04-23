package com.example.smartwatch_app.database_classes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DatabaseQuery extends DatabaseObject{
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();
    public DatabaseQuery(Context context) {
        super(context);
    }

    public List<EventObjects> getAllFutureEvents(){

        Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        String query = "SELECT * FROM calendar";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(1);
                String date = cursor.getString(2);

                Log.d(TAG, "ID: " + id);
                Log.d(TAG, "message: " + message);
                Log.d(TAG, "date: " + date);

                //convert start date to date object
                Date date_string = convertStringToDate(date);
//                if(date_string.before(dateToday) || date_string.equals(dateToday)){
//                    events.add(new EventObjects(id, message, date_string));
//                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
