package com.cyprian.money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private DatabaseReference mIncome, mExpense;
    private FirebaseAuth mAuth;
    private TextView totalIncome;
    private TextView totalExpense;

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

        mAuth = FirebaseAuth.getInstance();
        mIncome = FirebaseDatabase.getInstance().getReference().child("income").child(mAuth.getCurrentUser().getUid());
        mExpense = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());

        totalIncome = findViewById(R.id.incTot);
        totalExpense = findViewById(R.id.expTot);
    }

    @Override
    public void onStart() {
        super.onStart();

        Query incQuery = mIncome.orderByChild("income");

        incQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totAmt = 0;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) data.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totAmt += pTotal;
                }
                totalIncome.setText("Ksh. " + totAmt);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query expQuery = mExpense.orderByChild("expense");
        expQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                int totAmt = 0;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) data.getValue();
                    Object total = map.get("expenseAmount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totAmt += pTotal;
                }
                totalExpense.setText("Ksh. " + totAmt);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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