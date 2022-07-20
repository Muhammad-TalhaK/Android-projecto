package com.example.to_dolist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.Adapter.rcvAdapter;
import com.example.to_dolist.Model.Note;
import com.example.to_dolist.View.InsertActivity;
import com.example.to_dolist.View.UpdateActivity;
import com.example.to_dolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NoteViewModel noteViewModel;
    rcvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AA8108")));

        //viewModel initialization
        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        binding.floatingActionButton.setOnClickListener(v -> {
            Intent intent= new Intent(MainActivity.this,InsertActivity.class);
            startActivityForResult(intent,1);
        });

        //Swiping Functions
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                }

                else{
                    Intent intent2 = new Intent(MainActivity.this, UpdateActivity.class);
                    intent2.putExtra("titleUp",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent2.putExtra("DescUp",adapter.getNote(viewHolder.getAdapterPosition()).getDesc());
                    intent2.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent2,2);

                }

            }
        }).attachToRecyclerView(binding.rcv);

        //recyclerview binding
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new rcvAdapter();
        binding.rcv.setAdapter(adapter);
        binding.rcv.hasFixedSize();

        //Populating rcv with data from db
        noteViewModel.getAllNotes().observe(this, notes -> adapter.submitList(notes));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
           String title= data.getStringExtra("title");
           String desc= data.getStringExtra("Description");
            Note note = new Note(title,desc);
            noteViewModel.insert(note);
            Toast.makeText(MainActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
        }

        if(requestCode==2){
            String titleUp= data.getStringExtra("titleUp");
            String descUp= data.getStringExtra("DescriptionUp");
            int id = data.getIntExtra("id",0);
            Note note = new Note(titleUp,descUp);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}