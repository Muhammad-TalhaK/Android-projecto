package com.example.to_dolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.to_dolist.Db.NoteDao;
import com.example.to_dolist.Db.NoteDatabase;
import com.example.to_dolist.Model.Note;

import java.util.List;

public class Repository {
    private NoteDao noteDao;
    private LiveData<List<Note>> noteList;

    public Repository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        noteList= noteDao.getAllData();
    }

    public void insertData(Note note){new InsertTask(noteDao).execute(note);}
    public void deleteData(Note note){new DeleteTask(noteDao).execute(note);}
    public void updateData(Note note){new UpdateTask(noteDao).execute(note);}

    public LiveData<List<Note>>showData(){
        return noteList;
    }

    //insert task
    public static class InsertTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public InsertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    //delete task
    public static class DeleteTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public DeleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    //update task
    public static class UpdateTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public UpdateTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
}
