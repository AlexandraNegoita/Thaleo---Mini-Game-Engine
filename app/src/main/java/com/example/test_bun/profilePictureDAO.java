package com.example.test_bun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class profilePictureDAO {
    private static final String URL = "jdbc:mysql://192.168.1.124:3309/thaleodb";
    private static final String USER = "root";
    private static final String PASSWORD = "11111111";
    private static profilePictureDAO instance;
    private profilePicture profilePicture;

    private profilePictureDAO(){}

    public static synchronized profilePictureDAO getInstance(){
        if (instance == null) {
            instance = new profilePictureDAO();
        }
        return instance;
    }

    public profilePicture getProfilePicture(){
        return this.profilePicture;
    }
    public void setProfilePicture(int userID, Bitmap imageData){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);
            String bArray = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO profilepictures(userID, image) VALUES ('" + userID + "','" + bArray + "')");

            int rs = statement.executeUpdate();
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
    }    public profilePicture getPicture(int userID){
        ResultSet resultSet = null;
        profilePicture = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM profilepictures WHERE userID='" + userID + "'");

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                profilePicture = new profilePicture(
                        Integer.parseInt(resultSet.getString("imgID")),
                        Integer.parseInt(resultSet.getString("userID")),
                        resultSet.getBinaryStream("image")
                );
                //profilePicture.setImageFull(StringToBitMap(resultSet.getBinaryStream("image")));
            }
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
        return profilePicture;
    }
    public Bitmap StringToBitMap(InputStream encodedString) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(encodedString);
            return bitmap;
        } catch (Exception e) {
            Log.e("addsdd", e.getMessage());
            return null;
        }
    }

}
