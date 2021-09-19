package com.cyprian.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.myViewHolder>{

    private Context mContext;
    private List<Integer> myDataList;
    private List<Integer> dateList;


//    private String weeklyDate = "";
//    private int weeklyAmount = 0;

    public WeeklyAdapter(Context mContext, ArrayList<Integer> myDataList, ArrayList<Integer> dateList) {
        this.mContext = mContext;
        this.myDataList = (List<Integer>) myDataList;
        this.dateList = dateList;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_list_view, null);

        return new WeeklyAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        Integer expense = myDataList.get(position);
        Integer exp = dateList.get(position);

        holder.setWeeklyAmount(""+exp);
        holder.setWeeklyDate("Week "+expense);

    }

    @Override
    public int getItemCount() {
        return myDataList.size();
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
