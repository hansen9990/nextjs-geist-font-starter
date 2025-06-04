package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button homeButton, ourStoreButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        homeButton = findViewById(R.id.homeButton);
        ourStoreButton = findViewById(R.id.ourStoreButton);
        logoutButton = findViewById(R.id.logoutButton);

        viewPager.setAdapter(new BooksPagerAdapter());

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Non-Fiction");
                    } else {
                        tab.setText("Fiction");
                    }
                }).attach();

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BooksActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        ourStoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(BooksActivity.this, StoreActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(BooksActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private class BooksPagerAdapter extends RecyclerView.Adapter<BooksPagerAdapter.BooksViewHolder> implements ViewPager2.PageTransformer {

        @NonNull
        @Override
        public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView recyclerView = new RecyclerView(parent.getContext());
            recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
            return new BooksViewHolder(recyclerView);
        }

        @Override
        public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
            RecyclerView recyclerView = (RecyclerView) holder.itemView;
            ArrayList<Book> books = new ArrayList<>();
            if (position == 0) {
                // Non-Fiction books
                books.add(new Book("Non-fiction title 1", "Author 1"));
                books.add(new Book("Non-fiction title 2", "Author 2"));
                books.add(new Book("Non-fiction title 3", "Author 3"));
                books.add(new Book("Non-fiction title 4", "Author 4"));
                books.add(new Book("Non-fiction title 5", "Author 5"));
            } else {
                // Fiction books
                books.add(new Book("Fiction title 1", "Author A"));
                books.add(new Book("Fiction title 2", "Author B"));
                books.add(new Book("Fiction title 3", "Author C"));
                books.add(new Book("Fiction title 4", "Author D"));
                books.add(new Book("Fiction title 5", "Author E"));
            }
            BooksAdapter adapter = new BooksAdapter(books);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return 2; // Two tabs
        }

        @Override
        public void transformPage(@NonNull View page, float position) {
            // No animation for page transform
        }

        class BooksViewHolder extends RecyclerView.ViewHolder {
            public BooksViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    public static class Book {
        public String title;
        public String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }
    }

    public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

        private ArrayList<Book> books;

        public BooksAdapter(ArrayList<Book> books) {
            this.books = books;
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_book_detail, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = books.get(position);
            holder.bookTitleTextView.setText(book.title);
            holder.bookAuthorTextView.setText(book.author);
            holder.bookImageView.setImageResource(R.drawable.logo);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(BooksActivity.this, BookDetailActivity.class);
                intent.putExtra("title", book.title);
                intent.putExtra("author", book.author);
                intent.putExtra("coverResId", R.drawable.logo);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        class BookViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImageView;
            TextView bookTitleTextView;
            TextView bookAuthorTextView;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                bookImageView = itemView.findViewById(R.id.bookImageView);
                bookTitleTextView = itemView.findViewById(R.id.bookTitleTextView);
                bookAuthorTextView = itemView.findViewById(R.id.bookAuthorTextView);
            }
        }
    }
}
