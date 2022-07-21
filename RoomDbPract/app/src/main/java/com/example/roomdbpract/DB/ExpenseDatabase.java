package com.example.roomdbpract.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Expense.class,version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {

    public static final String DB_NAME="expenseDb";
    public static ExpenseDatabase instance;

    public static synchronized ExpenseDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,ExpenseDatabase.class,DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract ExpenseDao getDao();
}
