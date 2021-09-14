package com.cyprian.money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

//        Dashboard expenses
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_daily));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_weekly));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_monthly));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager);

        final ExpenseFragmentAdapter expenseFragmentAdapter = new ExpenseFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(expenseFragmentAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        End of dashboard expenses

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.categories:
                        Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.expense:
                        Intent intent2 = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.income:
                        Intent intent1 = new Intent(DashboardActivity.this, IncomeActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

//        //Initializing the recycler view
//        RecyclerView recyclerView = findViewById(R.id.dashboard_recyclerView);
//        //setting the layout manager for the recycler view
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //Initializing the array list that will contain the data
//        categoryArrayList = new ArrayList<>();
//        //Initializing the dailyAdapter
//        categoryAdapter = new CategoryAdapter(this, categoryArrayList);
//        //Setting the adapter
//        recyclerView.setAdapter(categoryAdapter);
//
//        //Getting the data
//        initializeData();
    }

//    private void initializeData() {
//        //Getting the data created in strings.xml
//        String[] categoryTitles = getResources().getStringArray(R.array.category_titles);
//        String[] categoryAmounts = getResources().getStringArray(R.array.category_amounts);
//
//        //clearing existing data to avoid duplication
//        categoryArrayList.clear();
//
//        for (int i = 0; i < categoryTitles.length; i++) {
//            categoryArrayList.add(new Category(categoryTitles[i], categoryAmounts[i]));
//        }
//
//        //Notify the adapter of the change in data set
//        categoryAdapter.notifyDataSetChanged();

        //Floating Action Button
//        FloatingActionButton fab = findViewById(R.id.fab_category);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

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