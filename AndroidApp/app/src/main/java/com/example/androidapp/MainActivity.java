package com.example.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImageView = findViewById(R.id.logoImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        loginButton.setBackgroundColor(Color.parseColor("#3E7C6B")); // Darker green on press
                        return true;
                    case MotionEvent.ACTION_UP:
                        loginButton.setBackgroundColor(Color.parseColor("#4C8C7F")); // Original green
                        performLogin();
                        return true;
                }
                return false;
            }
        });
    }

    private void performLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username must be filled");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password must be filled");
            return;
        }

        if (!password.matches("[a-zA-Z0-9]+")) {
            passwordEditText.setError("Password must be alphanumeric");
            return;
        }

        // Store username globally
        GlobalData.username = username;

        // Redirect to home page
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
