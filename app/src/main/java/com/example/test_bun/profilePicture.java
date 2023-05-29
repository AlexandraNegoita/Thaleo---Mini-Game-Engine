package com.example.test_bun;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.sql.Blob;

public class profilePicture {
    int imgID;
    int userID;
    InputStream image;

    public Bitmap getImageFull() {
        return imageFull;
    }

    public void setImageFull(Bitmap imageFull) {
        this.imageFull = imageFull;
    }

    Bitmap imageFull;

    public profilePicture(int imgID, int userID, InputStream image) {
        this.imgID = imgID;
        this.userID = userID;
        this.image = image;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
