package com.example.adapterproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnUserClickListener {

    RecyclerView verticalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalRecyclerView = findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data
        List<List<User>> parentList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            List<User> childList = new ArrayList<>();
            for (int j = 1; j <= 8; j++) {
                childList.add(new User("User " + j, "Detail " + j));
            }
            parentList.add(childList);
        }

        VerticalAdapter verticalAdapter = new VerticalAdapter(parentList, this);
        verticalRecyclerView.setAdapter(verticalAdapter);
    }

    @Override
    public void onUserClick(User user) {
        Toast.makeText(this, "Clicked: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
}