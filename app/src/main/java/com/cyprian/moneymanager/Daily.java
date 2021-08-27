package com.cyprian.moneymanager;

public class Daily {
    //Declaring member variables
    private String desc_title;
    private String dates;
    private String days;

    //A constructor for the daily expenses model
    Daily(String desc_title, String dates, String days){
        this.desc_title = desc_title;
        this.dates = dates;
        this.days = days;
    }

    //Getters to return a specific object
    public String getDesc_title() {
        return desc_title;
    }

    public String getDates() {
        return dates;
    }

    public String getDays() {
        return days;
    }
}
