package com.example.project01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    TextView tvName, tvAge, tvEmail;
    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tvName = findViewById(R.id.avName);
        tvAge = findViewById(R.id.avAge);
        tvEmail = findViewById(R.id.avEmail);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String email = getIntent().getStringExtra("email");

        tvName.setText("Name: " + name);
        tvAge.setText("Age: " + age);
        tvEmail.setText("Email: " + email);

        tvName.setOnClickListener(v -> saveToPrefs("name", name));
        tvAge.setOnClickListener(v -> saveToPrefs("age", age));
        tvEmail.setOnClickListener(v -> saveToPrefs("email", email));
    }

    private void saveToPrefs(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        Toast.makeText(this, key + " saved!", Toast.LENGTH_SHORT).show();
    }
}
