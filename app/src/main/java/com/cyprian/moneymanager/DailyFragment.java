package com.cyprian.moneymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {
    //Declaring private member variables
    private RecyclerView dailyRecyclerView;
    private ArrayList<Daily> myDailyData;
    private DailyAdapter dailyAdapter;

    public DailyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);

        //Initializing the recycler view
        dailyRecyclerView = rootView.findViewById(R.id.recycler_daily);
        //Set a layout for the recycler view
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Initializing the array list that will contain the data
        myDailyData = new ArrayList<>();
        //Initializing the dailyAdapter
        dailyAdapter = new DailyAdapter(myDailyData, getContext());
        //Setting the adapter
        dailyRecyclerView.setAdapter(dailyAdapter);
        //Getting the data
        initializeData();

        return rootView;
    }

    private void initializeData() {
        //Getting the data created in strings.xml
        String[] dailyTitles = getResources().getStringArray(R.array.desc_expenses);
        String[] dailyDays = getResources().getStringArray(R.array.days);
        String[] dailyDates = getResources().getStringArray(R.array.dates);

        //clearing existing data to avoid duplication
        myDailyData.clear();

        //creating an array list of dailyTitles, dailyDates and dailyDays
        for (int i = 0; i < dailyTitles.length; i++) {
            myDailyData.add(new Daily(dailyTitles[i], dailyDates[i], dailyDays[i]));
        }

        //Notify the adapter of the change in data set
        dailyAdapter.notifyDataSetChanged();
    }
}