package com.cyprian.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class IncomeExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_expense);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.income_expense_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Income"));
        tabLayout.addTab(tabLayout.newTab().setText("Expense"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        final ViewPager viewPager = findViewById(R.id.income_expense_viewPager);

        final IncomeExpenseFragmentAdapter incomeExpenseFragmentAdapter = new IncomeExpenseFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(incomeExpenseFragmentAdapter);

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

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
//
////        bottomNavigationView.setSelectedItemId(R.id.income);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                int id = item.getItemId();
//
//                switch (id) {
//                    case R.id.dashboard:
//                        Intent intent2 = new Intent(IncomeExpenseActivity.this, DashboardActivity.class);
//                        startActivity(intent2);
//                        break;
//                    case R.id.categories:
//                        Intent intent = new Intent(IncomeExpenseActivity.this, CategoryActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.expense:
//                        Intent intent1 = new Intent(IncomeExpenseActivity.this, MainActivity.class);
//                        startActivity(intent1);
//                        break;
//
//                    case R.id.income:
//                        Intent intent3 = new Intent(IncomeExpenseActivity.this, IncomeActivity.class);
//                        startActivity(intent3);
//                        break;
//                }
//                return false;
//            }
//        });
    }
    
}