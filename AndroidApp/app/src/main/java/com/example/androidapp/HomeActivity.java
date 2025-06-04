package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private RecyclerView booksRecyclerView;
    private ImageView storeImageView;
    private Button prevButton, nextButton;
    private Button allBooksButton, ourStoreButton, logoutButton;

    private ArrayList<Integer> storeImages;
    private int currentStoreIndex = 0;
    private Handler handler = new Handler();
    private Runnable carouselRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        greetingTextView = findViewById(R.id.greetingTextView);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        storeImageView = findViewById(R.id.storeImageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        allBooksButton = findViewById(R.id.allBooksButton);
        ourStoreButton = findViewById(R.id.ourStoreButton);
        logoutButton = findViewById(R.id.logoutButton);

        greetingTextView.setText("Hello, " + GlobalData.username);

        setupBooksRecyclerView();
        setupStoreCarousel();
        setupMenuButtons();
    }

    private void setupBooksRecyclerView() {
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<Book> books = new ArrayList<>();
        // Add 4 sample books
        books.add(new Book("Title 1", R.drawable.logo));
        books.add(new Book("Title 2", R.drawable.logo));
        books.add(new Book("Title 3", R.drawable.logo));
        books.add(new Book("Title 4", R.drawable.logo));

        BooksAdapter adapter = new BooksAdapter(books);
        booksRecyclerView.setAdapter(adapter);
    }

    private void setupStoreCarousel() {
        storeImages = new ArrayList<>();
        // Add sample store images (using logo placeholder)
        storeImages.add(R.drawable.logo);
        storeImages.add(R.drawable.logo);
        storeImages.add(R.drawable.logo);

        storeImageView.setImageResource(storeImages.get(currentStoreIndex));

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStoreIndex = (currentStoreIndex - 1 + storeImages.size()) % storeImages.size();
                animateStoreImageChange();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStoreIndex = (currentStoreIndex + 1) % storeImages.size();
                animateStoreImageChange();
            }
        });

        carouselRunnable = new Runnable() {
            @Override
            public void run() {
                currentStoreIndex = (currentStoreIndex + 1) % storeImages.size();
                animateStoreImageChange();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(carouselRunnable, 5000);
    }

    private void animateStoreImageChange() {
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) { }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                storeImageView.setImageResource(storeImages.get(currentStoreIndex));
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(500);
                fadeIn.setFillAfter(true);
                storeImageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) { }
        });
        storeImageView.startAnimation(fadeOut);
    }

    private void setupMenuButtons() {
        allBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, BooksActivity.class);
                startActivity(intent);
            }
        });

        ourStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(carouselRunnable);
    }

    // Book model class
    public static class Book {
        public String title;
        public int imageResId;

        public Book(String title, int imageResId) {
            this.title = title;
            this.imageResId = imageResId;
        }
    }

    // BooksAdapter for RecyclerView
    public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

        private ArrayList<Book> books;

        public BooksAdapter(ArrayList<Book> books) {
            this.books = books;
        }

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_book, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BookViewHolder holder, int position) {
            Book book = books.get(position);
            holder.titleTextView.setText(book.title);
            holder.bookImageView.setImageResource(book.imageResId);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        class BookViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImageView;
            TextView titleTextView;

            public BookViewHolder(View itemView) {
                super(itemView);
                bookImageView = itemView.findViewById(R.id.bookImageView);
                titleTextView = itemView.findViewById(R.id.bookTitleTextView);
            }
        }
    }
}
