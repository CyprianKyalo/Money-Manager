package com.cyprian.money;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {
    private EditText TitleExp, NoteExp, ExpenseAmt;
    private Button sendExpense;

    private DatabaseReference expenseRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    //Constructor
    public AddExpenseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);

        mAuth = FirebaseAuth.getInstance();
        expenseRef = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());
        loader = new ProgressDialog(getContext());

        TitleExp = rootView.findViewById(R.id.title_expense);
        NoteExp = rootView.findViewById(R.id.note_expense);
        ExpenseAmt = rootView.findViewById(R.id.expense_amt);
        sendExpense = rootView.findViewById(R.id.btn_add_expense);

        sendExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titleExp = TitleExp.getText().toString().trim();
                final String noteExp = NoteExp.getText().toString().trim();
                final String expenseAmt = ExpenseAmt.getText().toString().trim();

                if (TextUtils.isEmpty(titleExp)) {
                    TitleExp.setError("Expense Title Required!");
                    return;
                }


                if (TextUtils.isEmpty(expenseAmt)) {
                    ExpenseAmt.setError("Expense Amount Required!");
                    return;
                }

                else {
                    loader.setMessage("Adding Expense");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String id = expenseRef.push().getKey();
//                    String mDate = DateFormat.getDateInstance().format(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy");
                    String mDate = simpleDateFormat.format(new Date());

//                    Toast.makeText(getContext(), "Clicked yes", Toast.LENGTH_SHORT).show();
//
//                    System.out.println("Current Date: " + mDate);
//
//
//                    System.out.println("Current Day: " + mDate);

                    Expense expense = new Expense(titleExp, expenseAmt, noteExp, mDate);
                    expenseRef.child(id).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Expense Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                            loader.dismiss();
                        }
                    });
                }


            }
        });


        return rootView;
    }
}