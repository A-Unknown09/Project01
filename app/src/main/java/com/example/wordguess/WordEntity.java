package com.example.wordguess;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String word;
    public int level; // 1 = Easy, 2 = Medium, 3 = Hard

    public WordEntity(String word, int level) {
        this.word = word;
        this.level = level;
    }
}