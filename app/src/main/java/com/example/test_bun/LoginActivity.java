package com.example.test_bun;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    UserDAO userConn = UserDAO.getInstance();
    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    String usernameText;
    String passwordText;
    Button btnLogin;
    Button btnSignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = (TextInputEditText) findViewById(R.id.usernameInput);
        passwordInput = (TextInputEditText) findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                usernameText = usernameInput.getText().toString();
                passwordText = passwordInput.getText().toString();
                new InfoAsyncTask().execute();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                replaceActivity();
            }
        });
    }

    private void replaceActivity(){
        Intent k = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(k);
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

                User user = userConn.login(usernameText, passwordText);
                    info.put("username", user.getUsername());
                    info.put("password", user.getPassword());
                    info.put("email", user.getEmail());
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (!result.isEmpty()) {
               replaceActivity();
            }
        }
        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_login, fragment);
            fragmentTransaction.commit();
        }
        private void replaceActivity(){
            Intent k = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(k);
        }

    }
}