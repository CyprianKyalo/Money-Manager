package com.cyprian.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


//The DailyAdapter bridges between the Daily Class and the DailyFragment
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    //Declaring private member variables Daily Data and the context
    private ArrayList<Daily> dailyData;
    private Context myContext;
    private DatabaseReference mExpDat;
    private FirebaseAuth mAuth;

    //The constructor is for passing in array list data and the context
    DailyAdapter(ArrayList<Daily> mdailyData, Context context) {
        this.myContext = context;
        this.dailyData = mdailyData;
    }


    //This method creates view holder objects
    @NonNull
    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create a new viewholder
        return new ViewHolder(LayoutInflater.from(myContext).inflate(R.layout.daily_list_view, parent, false));
    }


    //This methods binds the view to the data
    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.ViewHolder holder, int position) {
        //Getting the current view object using its position and populating it with data
        Daily currentDaily = dailyData.get(position);

        //populating the current view with data
        holder.bindTo(currentDaily);
    }

    @Override
    public int getItemCount() {
        return dailyData.size();
    }

    //The view holder represents each and every row in the recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Declaring private member variables the viewholder will hold
        View mview;
        private TextView dailyExpenseTitle;
        private TextView dailyExpenseDate;
        private TextView dailySpendAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
//            dailyExpenseTitle = itemView.findViewById(R.id.expense_desc);
//            dailyExpenseDate = itemView.findViewById(R.id.daily_day);
//            dailySpendAmount = itemView.findViewById(R.id.spend);
        }

        private void setExpenseTitle(String expTitle) {
            TextView mTitle = mview.findViewById(R.id.expense_desc);
            mTitle.setText(expTitle);
        }

        private void setSpend(String spend) {
            TextView mspend = mview.findViewById(R.id.spend);

            String amount = String.valueOf(mspend);
            mspend.setText(amount);
        }

        private void setDate(String date) {
            TextView mdate = mview.findViewById(R.id.daily_date);
            mdate.setText(date);
        }

        //Binds a current view with the data
        public void bindTo(Daily currentDaily) {
            //Populating view with the data
            dailyExpenseTitle.setText(currentDaily.getExpenseTitle());
            dailyExpenseDate.setText(currentDaily.getDate());
            dailySpendAmount.setText(currentDaily.getSpend());
        }
    }
}
