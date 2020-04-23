package com.example.smartwatch_database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.List;
import org.tensorflow.lite.Interpreter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> quotes = databaseAccess.getQuotes();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quotes);
        listView.setAdapter(adapter);
    }
}
 
