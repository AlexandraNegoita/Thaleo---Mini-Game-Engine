package com.example.test_bun;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    UserDAO userConn = UserDAO.getInstance();
    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    TextInputEditText firstNameInput;
    TextInputEditText lastNameInput;
    TextInputEditText emailInput;
    TextInputEditText confPasswordInput;
    String usernameText;
    String passwordText;
    String firstNameText;
    String lastNameText;
    String confPasswordText;
    String emailText;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usernameInput = (TextInputEditText) findViewById(R.id.usernameInput);
        passwordInput = (TextInputEditText) findViewById(R.id.passwordInput);
        firstNameInput = (TextInputEditText) findViewById(R.id.firstNameInput);
        lastNameInput = (TextInputEditText) findViewById(R.id.lastNameInput);
        emailInput = (TextInputEditText) findViewById(R.id.emailInput);
        confPasswordInput = (TextInputEditText) findViewById(R.id.confPasswordInput);
        btnLogin = findViewById(R.id.btnSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                usernameText = usernameInput.getText().toString();
                passwordText = passwordInput.getText().toString();
                new InfoAsyncTask().execute();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            userConn.register(usernameText, passwordText, firstNameText, lastNameText, emailText);
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
        private void replaceActivity(){
            Intent k = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(k);
        }

    }
}
