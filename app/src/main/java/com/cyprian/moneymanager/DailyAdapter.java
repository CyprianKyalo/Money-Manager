package com.cyprian.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//The DailyAdapter bridges between the Daily Class and the DailyFragment
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    //Declaring private member variables Daily Data and the context
    private ArrayList<Daily> dailyData;
    private Context myContext;

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
        private TextView DailyTitle;
        private TextView DailyDate;
        private TextView DailyDays;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DailyTitle = itemView.findViewById(R.id.expense_desc);
            DailyDate = itemView.findViewById(R.id.daily_day);
            DailyDays = itemView.findViewById(R.id.daily_day_name);
        }

        //Binds a current view with the data
        public void bindTo(Daily currentDaily) {
            //Populating view with the data
            DailyTitle.setText(currentDaily.getDesc_title());
            DailyDate.setText(currentDaily.getDates());
            DailyDays.setText(currentDaily.getDays());
        }
    }
}
