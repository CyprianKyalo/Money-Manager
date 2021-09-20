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
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView crecyclerView;

    private DatabaseReference mCategories;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter adapter;

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

        mAuth = FirebaseAuth.getInstance();
        mCategories = FirebaseDatabase.getInstance().getReference().child("categories").child(mAuth.getCurrentUser().getUid());


        //Initializing the recycler view
        crecyclerView = findViewById(R.id.recycler_category);
        //setting the layout manager for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//        linearLayoutManager.setReverseLayout(true);
//        crecyclerView.setHasFixedSize(true);
        crecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mCategories, Category.class)
                .build();

        FirebaseRecyclerAdapter<Category, CategoryActivity.CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public CategoryActivity.CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_items, null);
                return new CategoryActivity.CategoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull CategoryActivity.CategoryViewHolder holder, int position, @NonNull @NotNull Category model) {
                holder.setCategoryName(model.getCategory_Title());
            }
        };

        crecyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setCategoryName(String catName) {
            TextView categName = mview.findViewById(R.id.category_title);
            categName.setText(catName);
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

    public void fab_cat(View view) {
        Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
        startActivity(intent);
    }
}