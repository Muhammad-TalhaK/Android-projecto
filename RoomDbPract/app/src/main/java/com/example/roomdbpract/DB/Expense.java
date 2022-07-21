package com.example.roomdbpract.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {

    private String title,amount;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Expense(String title, String amount) {
        this.title = title;
        this.amount = amount;
    }
}
