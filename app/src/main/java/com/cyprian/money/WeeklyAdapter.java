package com.cyprian.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.myViewHolder>{

    private Context mContext;
    private List<Expense> myDataList;

//    private String weeklyDate = "";
//    private int weeklyAmount = 0;

    public WeeklyAdapter(Context mContext, List<Expense> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
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
        final Expense expense = myDataList.get(position);

        holder.weeklyDate.setText(expense.getExpenseDate());
        holder.weeklyAmount.setText(expense.getExpenseAmount());

//        String resDate = model.getExpenseDate();
//        SimpleDateFormat sd = new SimpleDateFormat("E, dd MMM yyyy");
//        try {
//            Date date = sd.parse(resDate);
//            SimpleDateFormat sdi = new SimpleDateFormat("dd");
//            holder.setDay(sdi.format(date));
//            System.out.println("Current Day" + date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            Date date = sd.parse(resDate);
//            SimpleDateFormat sdn = new SimpleDateFormat("E");
//            holder.setDailyName(sdn.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Date date = sd.parse(resDate);
//            SimpleDateFormat sdn = new SimpleDateFormat("yyyy.MM");
//            holder.setDate(sdn.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


//                holder.setDate(model.getExpenseDate());
//        holder.setExpenseTitle(model.getExpenseTitle());
//        holder.setSpend("Ksh. " + model.getExpenseAmount());

    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public TextView weeklyDate, weeklyAmount;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
            weeklyDate = itemView.findViewById(R.id.weekly_date);
            weeklyAmount = itemView.findViewById(R.id.weekly_spend);
        }

//        private void setExpenseTitle(String expTitle) {
//            TextView mTitle = mview.findViewById(R.id.expense_desc);
//            mTitle.setText(expTitle);
//        }
//
//        private void setSpend(String spend) {
//            TextView mspend = mview.findViewById(R.id.spend);
//
////            String amount = String.valueOf(mspend);
//            mspend.setText(spend);
//        }
//
//        private void setDate(String date) {
//            TextView mdate = mview.findViewById(R.id.daily_date);
//            mdate.setText(date);
//        }
//
//        private void setDay(String day) {
//            TextView dailyDay = mview.findViewById(R.id.daily_day);
//            dailyDay.setText(day);
//        }
//
//        private void setDailyName(String name) {
//            TextView dailyName = mview.findViewById(R.id.daily_day_name);
//            dailyName.setText(name);
//        }
    }
}
