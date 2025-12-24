package com.example.wordguess;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord, tvMessage, tvScore, tvLevel;
    private EditText etLetter;
    private Button btnGuess;

    private WordDatabase db;
    private String selectedWord;
    private char[] hiddenWord;
    private Set<Character> guessedLetters = new HashSet<>();

    private int score = 0;
    private int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWord = findViewById(R.id.tvWord);
        tvMessage = findViewById(R.id.tvMessage);
        tvScore = findViewById(R.id.tvScore);
        tvLevel = findViewById(R.id.tvLevel);
        etLetter = findViewById(R.id.etLetter);
        btnGuess = findViewById(R.id.btnGuess);

        db = WordDatabase.getInstance(this);
        preloadWords();

        startLevel();

        btnGuess.setOnClickListener(v -> handleGuess());
    }

    private void preloadWords() {
        if (db.wordDao().countWords() == 0) {
            db.wordDao().insertAll(
                    new WordEntity("cat", 1),
                    new WordEntity("dog", 1),
                    new WordEntity("java", 1),

                    new WordEntity("android", 2),
                    new WordEntity("mobile", 2),
                    new WordEntity("developer", 2),

                    new WordEntity("architecture", 3),
                    new WordEntity("performance", 3),
                    new WordEntity("optimization", 3)
            );
        }
    }

    private void startLevel() {
        WordEntity wordEntity = db.wordDao().getRandomWordByLevel(level);
        selectedWord = wordEntity.word;

        hiddenWord = new char[selectedWord.length()];
        for (int i = 0; i < hiddenWord.length; i++) hiddenWord[i] = '_';

        guessedLetters.clear();
        updateUI();
    }

    private void handleGuess() {
        String input = etLetter.getText().toString().toLowerCase();
        etLetter.setText("");

        if (input.length() != 1) {
            tvMessage.setText("Enter one letter");
            return;
        }

        char letter = input.charAt(0);
        if (guessedLetters.contains(letter)) {
            tvMessage.setText("Already guessed");
            return;
        }

        guessedLetters.add(letter);
        boolean correct = false;

        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == letter) {
                hiddenWord[i] = letter;
                correct = true;
            }
        }

        if (correct) {
            score += 10;
            tvMessage.setText("Correct!");
        } else {
            tvMessage.setText("Wrong!");
        }

        if (new String(hiddenWord).equals(selectedWord)) {
            score += 50;
            level = Math.min(level + 1, 3);
            tvMessage.setText("🎉 Level Up!");
            startLevel();
        }

        updateUI();
    }

    private void updateUI() {
        StringBuilder display = new StringBuilder();
        for (char c : hiddenWord) display.append(c).append(" ");

        tvWord.setText(display.toString());
        tvScore.setText("Score: " + score);
        tvLevel.setText("Level: " + level);
    }
}
