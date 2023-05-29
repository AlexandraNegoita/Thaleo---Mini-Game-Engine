package com.example.test_bun;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PostDAO {
    private static final String URL = "jdbc:mysql://192.168.1.124:3309/thaleodb";
    public static int count = 0;
    private static final String USER = "root";
    private static final String PASSWORD = "11111111";
    private static PostDAO instance;
    private Post post;
    private ArrayList<Post> posts;

    private PostDAO(){}

    public static synchronized PostDAO getInstance(){
        if (instance == null) {
            instance = new PostDAO();
        }
        return instance;
    }

    public Post getPost(){
        return this.post;
    }
    public void setPost(int userID, String fileName, Bitmap imageData, String visibility){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);
            String bArray = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                    "posts(userID, fileName, file, visibility) " +
                    "VALUES ('" + userID + "', '" + fileName + "', '" + bArray + "', '" + visibility + "')");

            int rs = statement.executeUpdate();
            count++;
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
    }
    public ArrayList<Post> getPosts(int userID){
        ResultSet resultSet = null;
        post = null;
        posts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE userID='" + userID + "'");

            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                post = new Post(
                        Integer.parseInt(resultSet.getString("postID")),
                        Integer.parseInt(resultSet.getString("userID")),
                        resultSet.getString("fileName"),
                        resultSet.getBinaryStream("file"),
                        resultSet.getString("visibility")
                );
                posts.add(post);
                //profilePicture.setImageFull(StringToBitMap(resultSet.getBinaryStream("image")));
            }
        } catch (Exception e) {
            Log.e("InfoAsyncTask", "Error reading school information", e);
        }
        return posts;
    }

}
