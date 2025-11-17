package com.example.musicplayer;

public class Song_Activity {
    private String title;
    private int fileResId;

    public Song(String title, int fileResId) {
        this.title = title;
        this.fileResId = fileResId;
    }

    public String getTitle() {
        return title;
    }

    public int getFileResId() {
        return fileResId;
    }
}
