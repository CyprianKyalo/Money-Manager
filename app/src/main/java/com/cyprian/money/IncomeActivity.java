package com.cyprian.money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IncomeActivity extends AppCompatActivity {

    private ArrayList<Income> incomeArrayList;
    private IncomeAdapter incomeAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab_income);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(IncomeActivity.this, IncomeExpenseActivity.class);
////                startActivity(intent);
//                Fragment fragment = new AddIncomeFragment();
////                getSupportFragmentManager().beginTransaction().replace(R.i)
//            }
//        });

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.income);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.dashboard:
                        Intent intent2 = new Intent(IncomeActivity.this, DashboardActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.categories:
                        Intent intent = new Intent(IncomeActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.expense:
                        Intent intent1 = new Intent(IncomeActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

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
//        FloatingActionButton fab = findViewById(R.id.fab_income);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(IncomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
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

    public void fab(View view) {
        Intent intent = new Intent(IncomeActivity.this, IncomeExpenseActivity.class);
        startActivity(intent);
    }
}