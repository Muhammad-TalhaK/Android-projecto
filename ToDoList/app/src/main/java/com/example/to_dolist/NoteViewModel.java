package com.example.to_dolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_dolist.Model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Note>> noteList;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        noteList = repository.showData();
    }

    public LiveData<List<Note>> getAllNotes(){
        return repository.showData();
    }

    public void insert(Note note){
        repository.insertData(note);
    }
    public void delete(Note note){
        repository.deleteData(note);
    }
    public void update(Note note){
        repository.updateData(note);
    }
}
