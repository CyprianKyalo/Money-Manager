package com.cyprian.moneymanager;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.categories);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.dashboard:
                        Intent intent2 = new Intent(CategoryActivity.this, DashboardActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.expense:
                        Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.income:
                        Intent intent1 = new Intent(CategoryActivity.this, IncomeActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        //Floating Action Button
//        FloatingActionButton fab = findViewById(R.id.fab_category);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        //Initializing the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_category);
        //setting the layout manager for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initializing the array list that will contain the data
        categoryArrayList = new ArrayList<>();
        //Initializing the dailyAdapter
        categoryAdapter = new CategoryAdapter(this, categoryArrayList);
        //Setting the adapter
        recyclerView.setAdapter(categoryAdapter);

        //Getting the data
        initializeData();
    }

    private void initializeData() {
        //Getting the data created in strings.xml
        String[] categoryTitles = getResources().getStringArray(R.array.category_titles);
        String[] categoryAmounts = getResources().getStringArray(R.array.category_amounts);

        //clearing existing data to avoid duplication
        categoryArrayList.clear();

        for (int i = 0; i < categoryTitles.length; i++) {
            categoryArrayList.add(new Category(categoryTitles[i], categoryAmounts[i]));
        }

        //Notify the adapter of the change in data set
        categoryAdapter.notifyDataSetChanged();

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

    public void fab_cat(View view) {
        Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
        startActivity(intent);
    }
}