package com.example.wordguessinggame;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String word;

    public Word(String word) {
        this.word = word;
    }
}

