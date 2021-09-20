package com.cyprian.money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IncomeActivity extends AppCompatActivity {

    private ArrayList<Income> incomeArrayList;
    private IncomeAdapter incomeAdapter;
    private RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    private DatabaseReference mIncome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

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

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncome = FirebaseDatabase.getInstance().getReference().child("income").child(mAuth.getCurrentUser().getUid());


        //Initializing the recycler view
        recyclerView = findViewById(R.id.recycler_income);
        //setting the layout manager for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //Initializing the array list that will contain the data
//        incomeArrayList = new ArrayList<>();
//        //Initializing the dailyAdapter
//        incomeAdapter = new IncomeAdapter(this, incomeArrayList);
//        //Setting the adapter
//        recyclerView.setAdapter(incomeAdapter);

        //Getting the data


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Income> options = new FirebaseRecyclerOptions.Builder<Income>()
                .setQuery(mIncome, Income.class)
                .build();

        FirebaseRecyclerAdapter<Income, myViewHolder> adapter = new FirebaseRecyclerAdapter<Income, myViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_items, null);
                return new myViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position, @NonNull @NotNull Income model) {
                holder.setIncomeTitle(model.getIncome_title());
                holder.setIncomeAmt("Ksh. " + model.getAmount());
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    private static class myViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        private void setIncomeTitle(String incTitle) {
            TextView mTitle = mview.findViewById(R.id.income_title);
            mTitle.setText(incTitle);
        }

        private void setIncomeAmt(String incAmt) {
            TextView mspend = mview.findViewById(R.id.income_amt);
            mspend.setText(incAmt);
        }
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