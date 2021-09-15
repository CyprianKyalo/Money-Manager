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
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

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



        //Initializing the recycler view
        recyclerView = findViewById(R.id.recycler_category);
        //setting the layout manager for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

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
        mCategories = FirebaseDatabase.getInstance().getReference().child("categories");

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            updateUI(currentUser);
            adapter.startListening();
        }
    }

    private void updateUI(final FirebaseUser currentUser) {
        Query query = FirebaseDatabase.getInstance().getReference().child("categories");

//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(query, new SnapshotParser<Category>() {
//                    @NonNull
//                    @NotNull
//                    @Override
//                    public Category parseSnapshot(@NonNull @NotNull DataSnapshot snapshot) {
//                        return new Category(snapshot.child("category_Title").getValue().toString());
//                    }
//                }).build();

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mCategories, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position, @NonNull @NotNull Category model) {
                holder.setCategoryName(model.getCategory_Title());

            }

            @NonNull
            @NotNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_items, null);
                return new CategoryViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryName;

        String currentUserID;
        FirebaseAuth mAuth;
        DatabaseReference mCategories;
        View mview;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

//            categoryName = itemView.findViewById(R.id.input_category);

//            mCategories = FirebaseDatabase.getInstance().getReference().child("categories");
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