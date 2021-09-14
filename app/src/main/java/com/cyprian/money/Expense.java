package com.cyprian.money;

public class Expense {
    private String expenseTitle;
    private String expenseAmount;
    private String expenseNote;
    private String expenseDate;

    public Expense(String expenseTitle, String expenseAmount, String expenseNote, String expenseDate) {
        this.expenseTitle = expenseTitle;
        this.expenseAmount = expenseAmount;
        this.expenseNote = expenseNote;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    public String getExpenseTitle() {
        return expenseTitle;
    }

    public void setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
