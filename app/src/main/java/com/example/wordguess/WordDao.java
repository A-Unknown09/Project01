package com.example.wordguess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insertAll(WordEntity... words);

    @Query("SELECT * FROM words WHERE level = :level ORDER BY RANDOM() LIMIT 1")
    WordEntity getRandomWordByLevel(int level);

    @Query("SELECT COUNT(*) FROM words")
    int countWords();
}
