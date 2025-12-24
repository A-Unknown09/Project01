package com.example.wordguess;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WordEntity.class}, version = 1)
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase INSTANCE;

    public abstract WordDao wordDao();

    public static synchronized WordDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WordDatabase.class,
                    "word_db"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
