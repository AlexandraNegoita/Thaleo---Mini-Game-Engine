package com.example.test_bun;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends Activity {
    PostDAO post = PostDAO.getInstance();
    int currentPostID;
    ArrayList<Post> p;
    GalleryActivity ga = this;
    LinearLayout ll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ll = findViewById(R.id.galleryContainer);
        new InfoAsyncProfile().execute();
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncProfile extends AsyncTask<Void, Void, ArrayList<Post>> {
        @Override
        protected ArrayList<Post> doInBackground(Void... voids) {
            p = new ArrayList<>();
            p = post.getPosts(UserDAO.getInstance(getApplicationContext()).getUserData().getUserID());
            for(Post pp: p){
                Bitmap bm;
                Base64InputStream base64InputStream = new Base64InputStream(pp.getFile(),Base64.DEFAULT);
                bm = BitmapFactory.decodeStream(base64InputStream);
                pp.setFileFull(bm);
            }
            return p;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> result) {
            if (result != null) {
                int counter = 0;
                LinearLayout llh = new LinearLayout(ga);
                llh.setOrientation(LinearLayout.HORIZONTAL);
                llh.setPadding(0, 100, 0, 0);
                //llh.setTranslationY(160);
                ll.addView(llh);
                for(Post pp : result) {
                        if(counter%2 == 0){
                            llh = new LinearLayout(ga);
                            llh.setOrientation(LinearLayout.HORIZONTAL);
                            llh.setPadding(0, 100, 0, 0);
                           // llh.setTranslationY(330);
                            ll.addView(llh);
                        }
                        LinearLayout llimg = new LinearLayout(ga);
                        llimg.setOrientation(LinearLayout.VERTICAL);
                        ImageView iv = new ImageView(ga);
                        iv.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,680));
                        Bitmap cpy = pp.getFileFull();

                        iv.setImageBitmap(pp.getFileFull());

                        TextView tv = new TextView(ga);
                        tv.setTextSize(16);
                        tv.setText(pp.getFileName() + "\n(" + pp.getVisibility() + ")");
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);

                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContentView(R.layout.view_photo);
                                ImageView viewImage = findViewById(R.id.viewImage);
                                viewImage.setImageBitmap(cpy);
                                viewImage.setVisibility(View.VISIBLE);
                                //TextView textGallery = findViewById(R.id.textGallery);
                                //textGallery.setText(pp.getFileName());
                                Button btnPublic = findViewById(R.id.btnPostPublic);
                                if(pp.getVisibility().equals("public")) {
                                    btnPublic.setText("Set as Private Project");
                                } else {
                                    btnPublic.setText("Set as Public Project");
                                }
                                btnPublic.setVisibility(View.VISIBLE);
                                btnPublic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        currentPostID = pp.getPostID();
                                        if(pp.getVisibility().equals("public")){
                                            pp.setVisibility("private");
//                                          post.privatePost(pp.getPostID());
                                            new InfoAsyncPrivatePost().execute();

                                        } else {
                                            pp.setVisibility("public");
                                            //post.publicPost(pp.getPostID());
                                            new InfoAsyncPublicPost().execute();
                                        }
                                    }
                                });
                                viewImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent gallery = new Intent(GalleryActivity.this, GalleryActivity.class);
                                        startActivity(gallery);
//                                        viewImage.setVisibility(View.GONE);
//                                        btnPublic.setVisibility(View.GONE);
                                      //  textGallery.setText("My Projects");
                                    }
                                });
                            }
                        });



                    llimg.addView(iv);
                    llimg.addView(tv);
                    llh.addView(llimg);

                    counter++;
                }
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncPublicPost extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            post.publicPost(currentPostID);
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            Toast.makeText(getApplicationContext(), "Post set to Public", Toast.LENGTH_SHORT).show();

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncPrivatePost extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            post.privatePost(currentPostID);
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            Toast.makeText(getApplicationContext(), "Post set to Private", Toast.LENGTH_SHORT).show();

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncPPost extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            post.publicPost(currentPostID);
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {

        }
    }
}
