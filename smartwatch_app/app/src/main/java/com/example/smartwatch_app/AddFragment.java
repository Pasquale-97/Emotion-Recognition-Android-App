package com.example.smartwatch_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartwatch_app.database_classes.DatabaseOpenHelper;
import com.example.smartwatch_app.database_classes.EventObjects;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


import static android.content.ContentValues.TAG;
import static java.lang.System.currentTimeMillis;

public class AddFragment extends Fragment {

    Button btn;
    Button listOfEmotions;
    Button clearDatabase;

    DatabaseOpenHelper myDb = DatabaseOpenHelper.getInstance(getActivity());


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myDb= new DatabaseOpenHelper(getActivity());
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        //textView = (TextView) v.findViewById(R.id.emotion);


        btn = v.findViewById(R.id.machine_learning_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EmotionRecognition();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        listOfEmotions = v.findViewById(R.id.list_of_emotions_button);
        listOfEmotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ListOfEmotions();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

//        clearDatabase = v.findViewById(R.id.delete_button);
//        clearDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDb.deleteAll();
//            }
//        });

        return v;
    }
}
