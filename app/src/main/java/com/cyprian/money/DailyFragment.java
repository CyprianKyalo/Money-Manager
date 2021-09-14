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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {
    //Declaring private member variables
    private RecyclerView dailyRecyclerView;
    private ArrayList<Daily> myDailyData;
    private DailyAdapter dailyAdapter;

    private DatabaseReference mExpDat;
    private FirebaseAuth mAuth;

    public DailyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mExpDat = FirebaseDatabase.getInstance().getReference().child("expense").child(mAuth.getCurrentUser().getUid());


        //Initializing the recycler view
        dailyRecyclerView = rootView.findViewById(R.id.recycler_daily);
        //Set a layout for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        dailyRecyclerView.setHasFixedSize(true);
        dailyRecyclerView.setLayoutManager(linearLayoutManager);
//        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Initializing the array list that will contain the data
//        myDailyData = new ArrayList<>();
//        //Initializing the dailyAdapter
//        dailyAdapter = new DailyAdapter(myDailyData, getContext());
//        //Setting the adapter
//        dailyRecyclerView.setAdapter(dailyAdapter);
//        //Getting the data
//        initializeData();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Expense> options = new FirebaseRecyclerOptions.Builder<Expense>()
                .setQuery(mExpDat, Expense.class)
                .build();

        FirebaseRecyclerAdapter<Expense, myViewHolder> adapter = new FirebaseRecyclerAdapter<Expense, myViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_list_view, null);
                return new myViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position, @NonNull @NotNull Expense model) {
                String resDate = model.getExpenseDate();
                SimpleDateFormat sd = new SimpleDateFormat("E, dd MMM yyyy");
                try {
                    Date date = sd.parse(resDate);
                    SimpleDateFormat sdi = new SimpleDateFormat("dd");
                    holder.setDay(sdi.format(date));
                    System.out.println("Current Day" + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                try {
                    Date date = sd.parse(resDate);
                    SimpleDateFormat sdn = new SimpleDateFormat("E");
                    holder.setDailyName(sdn.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Date date = sd.parse(resDate);
                    SimpleDateFormat sdn = new SimpleDateFormat("yyyy.MM");
                    holder.setDate(sdn.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


//                holder.setDate(model.getExpenseDate());
                holder.setExpenseTitle(model.getExpenseTitle());
                holder.setSpend("Ksh. " + model.getExpenseAmount());

            }
        };

        dailyRecyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    private static class myViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
        }

        private void setExpenseTitle(String expTitle) {
            TextView mTitle = mview.findViewById(R.id.expense_desc);
            mTitle.setText(expTitle);
        }

        private void setSpend(String spend) {
            TextView mspend = mview.findViewById(R.id.spend);

//            String amount = String.valueOf(mspend);
            mspend.setText(spend);
        }

        private void setDate(String date) {
            TextView mdate = mview.findViewById(R.id.daily_date);
            mdate.setText(date);
        }

        private void setDay(String day) {
            TextView dailyDay = mview.findViewById(R.id.daily_day);
            dailyDay.setText(day);
        }

        private void setDailyName(String name) {
            TextView dailyName = mview.findViewById(R.id.daily_day_name);
            dailyName.setText(name);
        }
    }
}