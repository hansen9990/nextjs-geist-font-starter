package com.example.androidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private ImageView bookCoverImageView;
    private TextView bookTitleTextView, bookAuthorTextView;
    private EditText addressEditText, phoneEditText;
    private Button backButton, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookCoverImageView = findViewById(R.id.bookCoverImageView);
        bookTitleTextView = findViewById(R.id.bookTitleTextView);
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        backButton = findViewById(R.id.backButton);
        submitButton = findViewById(R.id.submitButton);

        // Get data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        int coverResId = intent.getIntExtra("coverResId", R.drawable.logo);

        bookTitleTextView.setText(title);
        bookAuthorTextView.setText(author);
        bookCoverImageView.setImageResource(coverResId);

        backButton.setOnClickListener(v -> {
            finish();
        });

        submitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        submitButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                        return true;
                    case MotionEvent.ACTION_UP:
                        submitButton.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        validateAndSubmit();
                        return true;
                }
                return false;
            }
        });
    }

    private void validateAndSubmit() {
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            showErrorDialog("Address must be filled");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showErrorDialog("Phone number must be filled");
            return;
        }

        if (!phone.matches("\\d+")) {
            showErrorDialog("Phone number must be numeric");
            return;
        }

        showSuccessDialog();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Validation Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("A confirmation email has been sent to your email address.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BookDetailActivity.this, BooksActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}
