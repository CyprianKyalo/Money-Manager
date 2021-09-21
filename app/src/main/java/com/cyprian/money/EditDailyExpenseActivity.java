package com.cyprian.money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditDailyExpenseActivity extends AppCompatActivity {
    private EditText TitleExp, NoteExp, ExpenseAmt;
    private Button editExpense;
    private Spinner categorySpinner;
    public static String categoryItem;
    String exp_ID = null;

    BottomNavigationView bottomNavigationView;

    private DatabaseReference mDatabase, categoryRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_daily_expense);

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
                        Intent intent2 = new Intent(EditDailyExpenseActivity.this, DashboardActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.expense:
                        Intent intent = new Intent(EditDailyExpenseActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.income:
                        Intent intent1 = new Intent(EditDailyExpenseActivity.this, IncomeActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        loader = new ProgressDialog(this);

        TitleExp = findViewById(R.id.title_expense);
        NoteExp = findViewById(R.id.note_expense);
        ExpenseAmt = findViewById(R.id.expense_amt);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());
        categoryRef = FirebaseDatabase.getInstance().getReference().child("categories").child(mAuth.getCurrentUser().getUid());

        exp_ID = getIntent().getExtras().getString("expenseID");
        editExpense = findViewById(R.id.btn_edit_expense);

        if (categorySpinner != null) {
            categorySpinner.setOnItemSelectedListener(null);
        }

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final List<String> categories = new ArrayList<String>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Category = dataSnapshot.child("category_Title").getValue(String.class);
                    categories.add(Category);
                }

                categorySpinner = findViewById(R.id.category_spinner);

                if (this != null){
                    //Create an Array Adapter using the string array and default spinner layout
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditDailyExpenseActivity.this, android.R.layout.simple_spinner_item, categories);
                    //Specify the layout for the drop down menu
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mDatabase.child(exp_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String titleExp = (String) snapshot.child("expenseTitle").getValue();
                String noteExp = (String) snapshot.child("expenseNote").getValue();
                String expAmt = (String) snapshot.child("expenseAmount").getValue();

                TitleExp.setText(titleExp);
                NoteExp.setText(noteExp);
                ExpenseAmt.setText(expAmt);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        editExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titleExp = TitleExp.getText().toString().trim();
                final String noteExp = NoteExp.getText().toString().trim();
                final String expenseAmt = ExpenseAmt.getText().toString().trim();
                final String catItem = categorySpinner.getSelectedItem().toString().trim();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("expenseTitle", titleExp);
                hashMap.put("expenseNote", noteExp);
                hashMap.put("expenseAmount", expenseAmt);
                hashMap.put("category", catItem);


                mDatabase.child(exp_ID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditDailyExpenseActivity.this, "Expense Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditDailyExpenseActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(EditDailyExpenseActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                        loader.dismiss();
                    }
                });
            }
        });
    }
}