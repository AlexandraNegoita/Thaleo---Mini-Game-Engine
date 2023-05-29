package com.example.test_bun;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import java.util.ArrayList;

public class GalleryActivity extends Activity {
    PostDAO post = PostDAO.getInstance();
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
            p = post.getPosts(UserDAO.getInstance().getUserData().getUserID());
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
                int counter = 1;
                LinearLayout llh = new LinearLayout(ga);
                llh.setOrientation(LinearLayout.HORIZONTAL);
                llh.setTranslationY(160);
                ll.addView(llh);
                for(Post pp : result) {
                        if(counter%3 == 0){
                            llh = new LinearLayout(ga);
                            llh.setOrientation(LinearLayout.HORIZONTAL);
                            llh.setTranslationY(330);
                            ll.addView(llh);
                        }
                        LinearLayout llimg = new LinearLayout(ga);
                        llimg.setOrientation(LinearLayout.VERTICAL);
                        ImageView iv = new ImageView(ga);
                        iv.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,680));
                        Bitmap cpy = pp.getFileFull();

                        iv.setImageBitmap(pp.getFileFull());

                        TextView tv = new TextView(ga);
                        tv.setText(pp.getVisibility() + "-" + pp.getFileName());
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);

                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageView viewImage = findViewById(R.id.viewImage);
                                viewImage.setImageBitmap(cpy);
                                viewImage.setVisibility(View.VISIBLE);
                                TextView textGallery = findViewById(R.id.textGallery);
                                textGallery.setText(pp.getFileName());
                                Button btnPublic = findViewById(R.id.btnPostPublic);
                                btnPublic.setVisibility(View.VISIBLE);
                                btnPublic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pp.setVisibility("public");
                                    }
                                });
                                viewImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        viewImage.setVisibility(View.GONE);
                                        btnPublic.setVisibility(View.GONE);
                                        textGallery.setText("My Projects");
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
}
