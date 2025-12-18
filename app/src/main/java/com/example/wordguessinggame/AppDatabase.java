package com.example.wordguessinggame;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//@Database(entities = {NameEntity.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase instance;
//
//    public abstract NameDao nameDao();
//
//    public static synchronized AppDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(
//                    context.getApplicationContext(),
//                    AppDatabase.class,
//                    "name_db"
//            ).allowMainThreadQueries().build();
//        }
//        return instance;
//    }
//}

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {NameEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NameDao nameDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_db"
                    )
                    .allowMainThreadQueries()   // 👈 دقیقاً اینجاست
                    .build();
        }
        return INSTANCE;
    }
}