package com.example.test.noteapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {


    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    private String description;
    private String date;


    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }


    public void setId(int id) {
        this.id = id;
    }
}
