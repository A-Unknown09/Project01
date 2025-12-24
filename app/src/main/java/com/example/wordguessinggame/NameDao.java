package com.example.wordguessinggame;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NameDao {
    @Insert
    void insert(Word word);

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT 1")
    Word getRandomWord();

    @Query("SELECT COUNT(*) FROM words")
    int countWords();
}
