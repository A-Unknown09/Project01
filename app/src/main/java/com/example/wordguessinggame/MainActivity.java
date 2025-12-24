package com.example.wordguessinggame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView wordTextView;
    EditText inputEditText;
    Button guessButton;

    Word currentWord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonHelper.loadWords(this);

        wordTextView = findViewById(R.id.wordTextView);
        inputEditText = findViewById(R.id.inputEditText);
        guessButton = findViewById(R.id.guessButton);

        loadNewWord();

        guessButton.setOnClickListener(v -> {
            String guess = inputEditText.getText().toString().trim();
            if (guess.equalsIgnoreCase(currentWord.word)) {
                wordTextView.setText("درست حدس زدی! 🎉");
                loadNewWord();
            } else {
                wordTextView.setText("اشتباه 😢 دوباره امتحان کن!");
            }
            inputEditText.setText("");
        });
    }

    private void loadNewWord() {
        currentWord = AppDatabase.getDatabase(this).wordDao().getRandomWord();
        String hint = "_".repeat(currentWord.word.length());
        wordTextView.setText(hint);
    }
}
