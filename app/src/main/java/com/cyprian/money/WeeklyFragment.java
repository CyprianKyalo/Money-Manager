package com.cyprian.money;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyFragment extends Fragment {

    private RecyclerView weeklyRecyclerView;
    private WeeklyAdapter weeklyAdapter;
    private List<Expense> myDataList;
    private TextView weeklySpend;

    private DatabaseReference mExpDat;
    private FirebaseAuth mAuth;
    private String onlineUserId = "";


    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);

        weeklySpend = rootView.findViewById(R.id.weekly_spend);

        weeklyRecyclerView = rootView.findViewById(R.id.recycler_weekly);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        weeklyRecyclerView.setHasFixedSize(true);
        weeklyRecyclerView.setLayoutManager(linearLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();

//        FirebaseUser mUser = mAuth.getCurrentUser();
//        String uid = mUser.getUid();

//        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());

        myDataList = new ArrayList<>();
        weeklyAdapter = new WeeklyAdapter(getContext(), myDataList);
        weeklyRecyclerView.setAdapter(weeklyAdapter);

        weekSpendingItems();

        return rootView;
    }

    private void weekSpendingItems() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());
        Query query = mExpDat.orderByChild("weeks").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                myDataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    myDataList.add(expense);
                }

                weeklyAdapter.notifyDataSetChanged();

                int totalAmt = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("expenseAmount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmt += pTotal;


                }
//                weeklySpend.setText(totalAmt);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}