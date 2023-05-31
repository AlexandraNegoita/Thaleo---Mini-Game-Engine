package com.example.test_bun;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends Activity {
    AdminDAO admin = AdminDAO.getInstance();
    int currentUser;
    String newUsername;
    ArrayList<User> u;
    AdminActivity ga = this;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
//        CardView newCard = new CardView(MainActivity.this);
//        getLayoutInflater().inflate(R.layout.card_base, newCard);
        ll = findViewById(R.id.adminContainer);
        new InfoAsyncUsers().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncUsers extends AsyncTask<Void, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            u = new ArrayList<>();
            u = admin.getUsers();
            return u;
        }

        @Override
        protected void onPostExecute(ArrayList<User> result) {
            if(result != null) {
                for(User uu : result) {
                    CardView userCard = new CardView(ga);
                    getLayoutInflater().inflate(R.layout.card_base, userCard);
                    TextView t1 = userCard.findViewById(R.id.textUser);
                    t1.setText(uu.getUsername() + "#" + uu.getUserID());

                    TextView t2 = userCard.findViewById(R.id.textName);
                    t2.setText(uu.getFirstName() + " " + uu.getLastName());

                    TextView t3 = userCard.findViewById(R.id.textRole);
                    t3.setText(uu.getRole());

                    Button btnUpdateUsername = userCard.findViewById(R.id.btnUpdateUsername);
                    TextInputEditText t4 = userCard.findViewById(R.id.newUsername);

                    btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentUser = uu.getUserID();
                            newUsername = t4.getText().toString();
                            //admin.changeUsername(currentUser,newUsername);
                            new InfoAsyncUpdateUsername().execute();
                        }
                    });

                    Button btnDeleteUser = userCard.findViewById(R.id.btnDeleteUser);
                    btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentUser = uu.getUserID();
                            //admin.deleteUser(currentUser);
                            new InfoAsyncDeleteUser().execute();
                        }
                    });
                    ll.addView(userCard);

                }

            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncUpdateUsername extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            admin.changeUsername(currentUser, newUsername);
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            Toast.makeText(getApplicationContext(), "Username updated", Toast.LENGTH_SHORT).show();

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncDeleteUser extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            admin.deleteUser(currentUser);
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            Toast.makeText(getApplicationContext(), "Deleted user", Toast.LENGTH_SHORT).show();

        }
    }
}
