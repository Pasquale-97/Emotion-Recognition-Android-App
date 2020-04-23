package com.example.smartwatch_app;

import android.graphics.Color;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.smartwatch_app.database_classes.DatabaseOpenHelper;
import com.example.smartwatch_app.database_classes.DatabaseQuery;
import com.example.smartwatch_app.database_classes.EventObjects;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class CalendarFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainTabView = inflater.inflate(R.layout.fragment_calendar, container, false);

        final List<String> mutableBookings = new ArrayList<>();

        final ListView bookingsListView = mainTabView.findViewById(R.id.bookings_listview);
        final Button showPreviousMonthBut = mainTabView.findViewById(R.id.prev_button);
        final Button showNextMonthBut = mainTabView.findViewById(R.id.next_button);
        //final Button slideCalendarBut = mainTabView.findViewById(R.id.slide_calendar);
        final Button showCalendarWithAnimationBut = mainTabView.findViewById(R.id.show_with_animation_calendar);

        //ORIGINAL
        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);

        //WHITE TEXT
        //final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.row, mutableBookings);
        bookingsListView.setAdapter(adapter);
        compactCalendarView = mainTabView.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        //compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.calendar_background));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));

        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);
        //compactCalendarView.setIsRtl(true);
        loadEvents();
        //loadEventsForYear(2017);
        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);


        // below line will display Sunday as the first day of the week
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        // disable scrolling calendar
        // compactCalendarView.shouldScrollMonth(false);

        // show days from other months as greyed out days
        //compactCalendarView.displayOtherMonthDays(true);

        // show Sunday as first day of month
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        //set initial title
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events ");

                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }

                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
            }
        });

        //final View.OnClickListener showCalendarOnClickLis = getCalendarShowLis();
        //slideCalendarBut.setOnClickListener(showCalendarOnClickLis);

        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
        showCalendarWithAnimationBut.setOnClickListener(exposeCalendarListener);

        compactCalendarView.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
            }
        });


        // uncomment below to show indicators above small indicator events
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);

        // uncomment below to open onCreate
        //openCalendarOnCreate(v);

        return mainTabView;
    }

//    @NonNull
//    private View.OnClickListener getCalendarShowLis() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!compactCalendarView.isAnimating()) {
//                    if (shouldShow) {
//                        compactCalendarView.showCalendar();
//                    } else {
//                        compactCalendarView.hideCalendar();
//                    }
//                    shouldShow = !shouldShow;
//                }
//            }
//        };
//    }

    @NonNull
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        // Set to current day on resume to set calendar to latest day
        toolbar.setTitle(dateFormatForMonth.format(new Date()));
    }

    Calendar calendar = Calendar.getInstance();
    long timeMilli2 = calendar.getTimeInMillis();

    private void loadEvents() {
        addEvents(0, 0);
        //addEvents(Calendar.DECEMBER, -1);
//      addEvents(Calendar.AUGUST, -1);
    }

//    private void loadEventsForYear(int year) {
//        addEvents(Calendar.DECEMBER, year);
//        addEvents(Calendar.AUGUST, year);
//    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();

//        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
//            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
//        }
    }


    public List<Event> getEvents (long timeInMillis){

        DatabaseOpenHelper db = new DatabaseOpenHelper(getActivity());
        ArrayList<EventObjects> new_events = db.getAllFutureEvents();


        String emotion_value = "";
        String date_value = "";

        int color = 0;

        for (Object i : new_events) {

            String data_from_db = String.valueOf(i);
            String[] parts = data_from_db.split(",");

            emotion_value = parts[0];
            date_value = parts[1];


            Log.e(TAG, "getEvents: " + date_value );

            if (emotion_value.equals("angry")) {
                color = Color.rgb(253, 103, 106);
            }

            if (emotion_value.equals("excited")) {
                color = Color.rgb(253, 153, 65);
            }

            if (emotion_value.equals("calm")) {
                color = Color.rgb(111, 176, 75);
            }

            if (emotion_value.equals("happy")) {
                color = Color.rgb(255, 253, 113);
            }

            if (emotion_value.equals("sad")) {
                color = Color.rgb(107, 205, 253);
            }

            DateFormat formatter = new SimpleDateFormat("hh:mm a");
            long milliSeconds= Long.parseLong(date_value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);

            compactCalendarView.addEvents(Arrays.asList(new Event(color, Long.parseLong(date_value), "Emotion: " + emotion_value.toUpperCase() + " detected at " + formatter.format(calendar.getTime()))));

        }

        return null;
    }


    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
//        for (int i = 0; i < 6; i++) {
//            currentCalender.setTime(firstDayOfMonth);
//            if (month > -1) {
//                currentCalender.set(Calendar.MONTH, month);
//            }
//            if (year > -1) {
//                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
//                currentCalender.set(Calendar.YEAR, year);
//            }
//            currentCalender.add(Calendar.DATE);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis);
            //compactCalendarView.addEvents(events);
//        }
    }

//    private List<Event> getEvents(long timeInMillis, int day) {
//        if (day < 2) {
//            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
//        } else if ( day > 2 && day <= 4) {
//            return Arrays.asList(
//                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
//                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
//        } else {
//            return Arrays.asList(
//                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
//                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
//                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
//        }
//    }




    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}

