package com.example.roomdbpract.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    public void insert(Expense expense);

    @Delete
    public void delete(Expense expense);

    @Update
    public void update(Expense expense);

    @Query("SELECT * FROM expense")
    public List<Expense> getAllExpense();
}
