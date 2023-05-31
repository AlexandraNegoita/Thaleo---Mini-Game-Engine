package com.example.test_bun;

import android.graphics.Bitmap;

import java.io.InputStream;

public class Post {
    int postID;
    int userID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;
    String fileName;
    InputStream file;

    public Bitmap getFileFull() {
        return fileFull;
    }

    public void setFileFull(Bitmap fileFull) {
        this.fileFull = fileFull;
    }

    Bitmap fileFull;
    String visibility;

    public Post(int postID, int userID, String username, String fileName, InputStream file, String visibility) {
        this.postID = postID;
        this.userID = userID;
        this.username = username;
        this.fileName = fileName;
        this.file = file;
        this.visibility = visibility;
    }


    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
