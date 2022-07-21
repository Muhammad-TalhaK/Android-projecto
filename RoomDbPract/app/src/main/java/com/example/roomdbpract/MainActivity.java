package com.example.roomdbpract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomdbpract.DB.Expense;
import com.example.roomdbpract.DB.ExpenseDatabase;
import com.example.roomdbpract.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ExpenseDatabase database;
    Adapter adapter;
    LinearLayout RlBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ExpenseDatabase.getInstance(this);
        adapter = new Adapter();

        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        binding.rcv.setAdapter(adapter);

        adapter.submitList(database.getDao().getAllExpense());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction==ItemTouchHelper.RIGHT){
                    database.getDao().delete(adapter.getExpense(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                   displayBottomSheet(adapter.getExpense(viewHolder.getAdapterPosition()));
                }

            }
        }).attachToRecyclerView(binding.rcv);

    binding.insertBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = binding.etTitle.getText().toString();
            String amount = binding.etAmount.getText().toString();
            Expense expense = new Expense(title,amount);
            database.getDao().insert(expense);
            Toast.makeText(MainActivity.this, "Record Added", Toast.LENGTH_SHORT).show();
            recreate();
        }
    });
    }

    private void displayBottomSheet(Expense expense){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bs_main,RlBottomSheet);

        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        EditText title = layout.findViewById(R.id.etTitle);
        EditText amount = layout.findViewById(R.id.etAmount);
        Button update = layout.findViewById(R.id.btnUpdate);

        title.setText(expense.getTitle());
        amount.setText(expense.getAmount());
        int id = expense.getId();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleNew = title.getText().toString();
                String amountNew = amount.getText().toString();
                Expense expense1 = new Expense(titleNew,amountNew);
                expense1.setId(id);
                database.getDao().update(expense1);
                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                recreate();
            }
        });
    }


}