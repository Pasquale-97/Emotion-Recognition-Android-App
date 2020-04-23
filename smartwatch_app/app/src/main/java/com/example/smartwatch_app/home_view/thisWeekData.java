package com.example.smartwatch_app.home_view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartwatch_app.HomeFragment;
import com.example.smartwatch_app.R;
import com.example.smartwatch_app.database_classes.DatabaseOpenHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.smartwatch_app.R.color.calendar_background;

public class thisWeekData extends Fragment {

    PieChart pieChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        TextView happyString = (TextView) v.findViewById(R.id.happy_string);
        TextView excitedString = (TextView) v.findViewById(R.id.excited_string);
        TextView calmString = v.findViewById(R.id.calm_string);
        TextView sadString = (TextView) v.findViewById(R.id.sad_string);
        TextView angryString = (TextView) v.findViewById(R.id.angry_string);

        DatabaseOpenHelper db = new DatabaseOpenHelper(getActivity());
        int excitedData = db.getWeekExcitedData();
        int happyData = db.getWeekHappyData();
        int calmData = db.getWeekCalmData();
        int sadData = db.getWeekSadData();
        int angryData = db.getWeekAngryData();


        String outputExcited = String.format("%s %5d", "EXCITED: ", excitedData);
        String outputHappy = String.format("%s %8d", "HAPPY: ", happyData);
        String outputCalm = String.format("%s %10d", "CALM: ", calmData);
        String outputSad = String.format("%s %13d", "SAD: ", sadData);
        String outputAngry = String.format("%s %8d", "ANGRY:", angryData);


        excitedString.setText(outputExcited);
        happyString.setText(outputHappy);
        calmString.setText(outputCalm);
        sadString.setText(outputSad);
        angryString.setText(outputAngry);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        pieChart = v.findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(getContext().getColor(R.color.calendar_background));
        pieChart.setTransparentCircleRadius(50f);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.animateY(1500, Easing.EasingOption.EaseInCubic);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(excitedData, "Excited"));
        yValues.add(new PieEntry(happyData, "Happy"));
        yValues.add(new PieEntry(calmData, "Calm"));
        yValues.add(new PieEntry(sadData, "Sad"));
        yValues.add(new PieEntry(angryData, "Angry"));

        PieDataSet dataSet = new PieDataSet(yValues, "Emotions");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        pieChart.getLegend().setEnabled(false);

        dataSet.setColors(getContext().getColor(R.color.excited_colour), getContext().getColor(R.color.happy_colour),
                getContext().getColor(R.color.calm_colour), getContext().getColor(R.color.sad_colour),
                getContext().getColor(R.color.angry_colour));

        PieData data = new PieData(dataSet);

        data.setValueTextSize(12f);
        data.setValueFormatter(new thisWeekData.DecimalRemover(new DecimalFormat("###,###,###")));
//      data.setValueFormatter(new thisWeekData.MyYAxisValueFormatter());

        pieChart.setData(data);

        return v;
    }

    public class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + "%";
        }
    }

//    public class MyYAxisValueFormatter implements IValueFormatter {
//
//        private DecimalFormat mFormat;
//
//        public MyYAxisValueFormatter() {
//            // format values to 1 decimal digit
//            mFormat = new DecimalFormat("###,###,##0.0");
//        }
//
//        @Override
//        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//            // "value" represents the position of the label on the axis (x or y)
//            if (value > 0) {
//                return mFormat.format(value);
//            } else {
//                return "";
//            }
//        }
//    }
}