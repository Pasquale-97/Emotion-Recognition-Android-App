package com.example.smartwatch_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartwatch_app.database_classes.DatabaseOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class ListOfEmotions extends Fragment {

    Button excitedButton;
    Button happyButton;
    Button calmButton;
    Button sadButton;
    Button angryButton;

    Button logEmotion;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    String dateString = dateFormat.format(date);


    DatabaseOpenHelper myDb = DatabaseOpenHelper.getInstance(getActivity());

    String emotion = "";
    int emotion_id = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.list_emotions, container, false);
        final TextView textView = (TextView)v.findViewById(R.id.emotion_text);
        myDb = new DatabaseOpenHelper(getActivity());

        logEmotion = v.findViewById(R.id.log_emotion);
        logEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emotion.equals("excited")){
                    emotion_id = 1;
                }

                if(emotion.equals("happy")){
                    emotion_id = 2;
                }

                if(emotion.equals("calm")){
                    emotion_id = 3;
                }

                if(emotion.equals("sad")){
                    emotion_id = 4;
                }

                if(emotion.equals("angry")){
                    emotion_id = 5;
                }

                boolean isInserted = myDb.insertData(emotion, emotion_id, dateString);


                if(textView.getText().toString().equals("HOW ARE YOU?")){
                    isInserted = false;
                }

                if (isInserted) {
                    Toast.makeText(getActivity(),"Emotion Added To Database", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(getActivity(), "Please Select An Emotion", Toast.LENGTH_LONG).show();
                }

                Log.d(TAG, "onClick: " + textView.getText().toString());
                Log.d(TAG, "onClick: " + dateString);
            }
        });

        excitedButton = v.findViewById(R.id.excited_button);
        excitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.excited);
                emotion = "excited";
            }
        });

        happyButton = v.findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.happy);
                emotion = "happy";
            }
        });

        calmButton = v.findViewById(R.id.calm_button);
        calmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.calm);
                emotion = "calm";
            }
        });


        sadButton = v.findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.sad);
                emotion = "sad";
            }
        });

        angryButton = v.findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.angry);
                emotion = "angry";
            }
        });

        return v;
    }
}
