package com.cyprian.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class IncomeActivity extends AppCompatActivity {

    private ArrayList<Income> incomeArrayList;
    private IncomeAdapter incomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Initializing the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_income);
        //setting the layout manager for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initializing the array list that will contain the data
        incomeArrayList = new ArrayList<>();
        //Initializing the dailyAdapter
        incomeAdapter = new IncomeAdapter(this, incomeArrayList);
        //Setting the adapter
        recyclerView.setAdapter(incomeAdapter);

        //Getting the data
        initializeData();

    }

    private void initializeData() {
        //Getting the data created in strings.xml
        String[] incomeTitles = getResources().getStringArray(R.array.income_titles);
        String[] incomeAmounts = getResources().getStringArray(R.array.income_amounts);

        //clearing existing data to avoid duplication
        incomeArrayList.clear();

        for (int i = 0; i < incomeTitles.length; i++) {
            incomeArrayList.add(new Income(incomeTitles[i], incomeAmounts[i]));
        }

        //Notify the adapter of the change in data set
        incomeAdapter.notifyDataSetChanged();

        //Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab_income);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.income) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}