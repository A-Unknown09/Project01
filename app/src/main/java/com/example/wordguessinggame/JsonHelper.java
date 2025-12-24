package com.example.wordguessinggame;
import android.content.Context;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonHelper {
    public static void loadWords(Context context) {
        try {
            InputStream is = context.getAssets().open("words.json");
            InputStreamReader reader = new InputStreamReader(is);
            Gson gson = new Gson();
            WordsList wordsList = gson.fromJson(reader, WordsList.class);

            AppDatabase db = AppDatabase.getDatabase(context);
            for (String w : wordsList.words) {
                db.wordDao().insert(new Word(w));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class WordsList {
        List<String> words;
    }
}

