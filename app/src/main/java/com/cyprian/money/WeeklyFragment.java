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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyFragment extends Fragment {

    private RecyclerView weeklyRecyclerView;
    private List<Expense> myDataList;
    private TextView weekSpend;
    private WeeklyAdapter weeklyAdapter;

    View rootView;
    private DatabaseReference mExpDat;
    private FirebaseAuth mAuth;

    private Integer key_amt, value_amt;
    HashMap hashMap = new HashMap();
    private ArrayList<Integer> weeklyData, weeklyDate;
    private ArrayList<Integer> weeklyAmts;


    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_weekly, container, false);

        weeklyRecyclerView = rootView.findViewById(R.id.recycler_weekly);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        weeklyRecyclerView.setHasFixedSize(true);
        weeklyRecyclerView.setLayoutManager(linearLayoutManager);

        weeklyData = new ArrayList<Integer>();
        weeklyDate = new ArrayList<Integer>();

        weeklyAdapter = new WeeklyAdapter(getContext(), weeklyData, weeklyDate);
        weeklyRecyclerView.setAdapter(weeklyAdapter);

        createHash();

        return rootView;
    }

    public void createHash() {
        mAuth = FirebaseAuth.getInstance();
        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());

        mExpDat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final List<Integer> weeks = new ArrayList<>();
                final List<Date> dates = new ArrayList();
                final List<Integer> amt = new ArrayList<>();
                int week = 0;
                int totAmt = 0;
                int totamt = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    Object objDate = map.get("expenseDate");

                    SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy");

                    try {
                        Date date = df.parse(String.valueOf(objDate));
                        System.out.println("Date is: "+date);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        week = cal.get(Calendar.WEEK_OF_YEAR);

                        weeks.add(week);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (week == 39) {
                        Object objAmt = map.get("expenseAmount");
                        int pTotal = Integer.parseInt(String.valueOf(objAmt));

                        totamt += pTotal;
                        hashMap.put(week, totamt);
                    } else if (week == 38) {
                        Object objamt = map.get("expenseAmount");
                        int Total = Integer.parseInt(String.valueOf(objamt));

                        totAmt += Total;
                        hashMap.put(week, totAmt);
                    }
                }

                for ( Object key : hashMap.keySet() ) {
                    System.out.println("The keys hare: "+key );
                }

                for (Object key : hashMap.keySet()) {
                    System.out.println("The keys from Bind are: "+key);
                    key_amt = (Integer) key;
                    value_amt = (Integer) hashMap.get(key);

                }

                for (int i = 0; i < hashMap.size(); i++) {
                    System.out.println("Hashmap keys are: "+hashMap);

                    System.out.println("Hashmap vals are: "+hashMap.get(i));
                }

                List<Integer> list = new ArrayList<Integer>(hashMap.values()); //Most likely dataSnapshot.getValues()

                weeklyData.clear();
                weeklyDate.clear();

                for (Object key : hashMap.keySet()) {
                    System.out.println("The keys from Bind are: "+key);
                    weeklyDate.add((Integer) key);

                }

                for (Object key : hashMap.values()) {
                    System.out.println("The values are: "+key);
                    weeklyData.add((Integer) key);
                }

                weeklyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}