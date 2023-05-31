package com.example.test_bun;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProfileFragment extends Activity {
    PostDAO post = PostDAO.getInstance();
    ArrayList<Post> p;
    MyProfileFragment ga = this;
    LinearLayout ll;
    UserDAO user = UserDAO.getInstance(ga);
    profilePictureDAO picture = profilePictureDAO.getInstance();
    String username;
    TextView usernameView;
    TextView userIdView;
    ImageView profilePicture;
    profilePicture pp;
    int userID;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_profile);
        //View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        usernameView = (TextView) findViewById(R.id.usernameProfile);
        userIdView = (TextView) findViewById(R.id.idProfile);
        usernameView.setText(user.getUserData().getUsername() + " #" + user.getUserData().getUserID());
        userIdView.setText(user.getUserData().getFirstName() + " " + user.getUserData().getLastName());
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        new InfoAsyncProfile().execute();

        ll = findViewById(R.id.publicPostsContainer);
        new InfoAsyncPublicPosts().execute();

        Button btnSignout = (Button) findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity();
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });

    }
    private void replaceActivity(){
        Intent k = new Intent(MyProfileFragment.this, LoginActivity.class);
        startActivity(k);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                new InfoAsyncTask().execute();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            picture.setProfilePicture(user.getUserData().getUserID(), bitmap);
            info.put("image", "true");
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (!result.isEmpty()) {
                ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncProfile extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bm = null;
            try {
                pp = picture.getPicture(user.getUserData().getUserID());
                Base64InputStream base64InputStream = new Base64InputStream(picture.getProfilePicture().getImage(),Base64.DEFAULT);
                bm = BitmapFactory.decodeStream(base64InputStream);
            } catch(Exception e) {

            }

//               // bm = BitmapFactory.decodeStream(pp.getImage().getBinaryStream());

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {

                profilePicture = (ImageView) findViewById(R.id.profilePicture);

                profilePicture.setImageBitmap(result);
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncPublicPosts extends AsyncTask<Void, Void, ArrayList<Post>> {
        @Override
        protected ArrayList<Post> doInBackground(Void... voids) {
            p = new ArrayList<>();
            p = post.getPublicPosts(user.getUserData().getUserID());
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
                  //  Bitmap cpy = pp.getFileFull();

                    iv.setImageBitmap(pp.getFileFull());

                    TextView tv = new TextView(ga);
                    tv.setTextSize(16);
                    tv.setText(pp.getFileName());
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);

//                    iv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ImageView viewImage = findViewById(R.id.viewImage);
//                            viewImage.setImageBitmap(cpy);
//                            viewImage.setVisibility(View.VISIBLE);
//                            TextView textGallery = findViewById(R.id.textGallery);
//                            textGallery.setText(pp.getFileName());
//                            Button btnPublic = findViewById(R.id.btnPostPublic);
//                            btnPublic.setVisibility(View.VISIBLE);
//                            btnPublic.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    pp.setVisibility("public");
//                                }
//                            });
//                            viewImage.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    viewImage.setVisibility(View.GONE);
//                                    btnPublic.setVisibility(View.GONE);
//                                    textGallery.setText("My Projects");
//                                }
//                            });
//                        }
//                    });

                    llimg.addView(iv);
                    llimg.addView(tv);
                    llh.addView(llimg);

                    counter++;
                }
            }
        }
    }
}