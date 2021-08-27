package com.cyprian.moneymanager;

public class Income {
    //Declare member variables
    private String income_title;
    private String amount;

    Income(String income_title, String amount) {
        this.income_title = income_title;
        this.amount = amount;
    }

    //Getters
    public String getIncome_title() {
        return income_title;
    }

    public String getAmount() {
        return amount;
    }
}
