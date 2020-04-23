package com.example.smartwatch_app.database_classes;

import java.util.ArrayList;

public class EventObjects {
    private int id;
    private String message;
    private int emotion_id;
    private long date;

    public EventObjects(int int_id, String s_message, int final_emotion_id, long s_date) {
        id = int_id;
        message = s_message;
        emotion_id = final_emotion_id;
        date = s_date;

    }

    @Override
    public String toString() {
        return message + "," + date;
    }

    public long getDate() {
        return date;
    }

    public String getEmotion() {
        return message;
    }
}