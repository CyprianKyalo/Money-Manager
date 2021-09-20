package com.cyprian.money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
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

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyFragment extends Fragment {

    private RecyclerView weeklyRecyclerView;
    private WeeklyAdapter weeklyAdapter;
    private List<Expense> myDataList;
    private TextView weekSpend;

    private DatabaseReference mExpDat;
    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    View rootView;


    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
//        LayoutInflater lf = getActivity().getLayoutInflater();
//        View view =  lf.inflate(R.layout.fragment_weekly, container, false); //pass the correct layout name for the fragment

//        LayoutInflater ifl = LayoutInflater.from(getContext());
//        View view = ifl.inflate(R.layout.weekly_list_view, null);

//        weekSpend = view.findViewById(R.id.week_spend);

//        weekSpend = getActivity().findViewById(R.id.week_spend);

//        String uid = mUser.getUid();
        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());



        weeklyRecyclerView = rootView.findViewById(R.id.recycler_weekly);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.removeAllViews();
        weeklyRecyclerView.setHasFixedSize(true);
        weeklyRecyclerView.setLayoutManager(linearLayoutManager);



//        FirebaseUser mUser = mAuth.getCurrentUser();
//        String uid = mUser.getUid();

//        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());

//        myDataList = new ArrayList<>();
//        weeklyAdapter = new WeeklyAdapter(getContext(), myDataList);
//        weeklyRecyclerView.setAdapter(weeklyAdapter);

//        weekSpend.setText(weekSpendingItems());

//        weekSpendingItems();


//        TextView text = (TextView) view.findViewById(R.id.week_spend);
//        text.setText(weekSpendingItems());

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

                System.out.println("Value " + myDataList);

                weeklyAdapter.notifyDataSetChanged();

                int totalAmt = 0;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("expenseAmount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmt += pTotal;

//                    weekSpend.setText(totalAmt);

                }


                weekSpend.setText(""+totalAmt);
                System.out.println("Value: " + totalAmt);
//                Expense expense = new Expense();
//                expense.setExpenseAmount(""+ totalAmt[0]);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

//        Query query = mExpDat.orderByChild("weeks").equalTo(weeks.getWeeks());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                //myDataList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Expense expense = dataSnapshot.getValue(Expense.class);
//                    myDataList.add(expense);
//                }
//
//                System.out.println("Value " + myDataList);
//
//                weeklyAdapter.notifyDataSetChanged();
//
//                int totalAmt = 0;
//
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
//                    Object total = map.get("expenseAmount");
//                    int pTotal = Integer.parseInt(String.valueOf(total));
//                    totalAmt += pTotal;
//
////                    weekSpend.setText(totalAmt);
//
//                }
//
//
//                weekSpend.setText(""+totalAmt);
//                System.out.println("Value: " + totalAmt);
////                Expense expense = new Expense();
////                expense.setExpenseAmount(""+ totalAmt[0]);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        Query query = mExpDat.orderByChild("weeks");
        final int[] sum = {0};
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//
//                    sum[0] += data.child("expenseAmount").getValue(Integer.class);
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        FirebaseRecyclerOptions<Expense> options = new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(query, Expense.class)
                .build();

        System.out.println("Options: "+options);



        FirebaseRecyclerAdapter<Expense, WeeklyFragment.myViewHolder> adapter = new FirebaseRecyclerAdapter<Expense, WeeklyFragment.myViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public WeeklyFragment.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_list_view, null);
                return new WeeklyFragment.myViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull WeeklyFragment.myViewHolder holder, int position, @NonNull @NotNull Expense model) {
//                final int[] sum = {0};

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int totAmt = 0;
                        String oldWeek = "";

                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            Map<String, Object> map = (Map<String, Object>) data.getValue();
                            Object total = map.get("expenseAmount");
                            int pTotal = Integer.parseInt(String.valueOf(total));
                            totAmt += pTotal;

//                            sum[0] += data.child("expenseAmount").getValue(Integer.class);

                        }
                        System.out.println(totAmt);
//                        if (!(oldWeek == ""+model.getWeeks())) {
//                            holder.setWeeklyAmount(""+totAmt);
//                            holder.setWeeklyDate(""+model.getExpenseDate());
//
//                        }
                        holder.setWeeklyAmount(""+totAmt);
                        holder.setWeeklyDate(""+model.getWeeks());

//                        oldWeek = ""+model.getWeeks();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                holder.setDate(model.getExpenseDate());
//                holder.setExpenseTitle(model.getExpenseTitle());
//                holder.setSpend("Ksh. " + model.getExpenseAmount());
//                System.out.println("Code: " + sum[0]);

//                holder.setWeeklyDate(""+model.getWeeks());
//                holder.setWeeklyAmount(""+sum[0]);

            }
        };

        weeklyRecyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    protected static class myViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        private void setWeeklyDate(String expTitle) {
            TextView mWeek = mview.findViewById(R.id.weekly_date);
            mWeek.setText(expTitle);
        }

        private void setWeeklyAmount(String spend) {
            TextView mspend = mview.findViewById(R.id.week_spend);

//            String amount = String.valueOf(mspend);
            mspend.setText(spend);
        }
    }

}