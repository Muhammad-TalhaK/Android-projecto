package com.example.to_dolist.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    private String title,desc;

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Note(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
}
