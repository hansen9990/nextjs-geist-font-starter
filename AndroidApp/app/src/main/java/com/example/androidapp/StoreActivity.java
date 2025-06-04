package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView storesRecyclerView;
    private Button homeButton, itemsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        storesRecyclerView = findViewById(R.id.storesRecyclerView);
        homeButton = findViewById(R.id.homeButton);
        itemsButton = findViewById(R.id.itemsButton);
        logoutButton = findViewById(R.id.logoutButton);

        storesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(new Store("Store 1"));
        stores.add(new Store("Store 2"));
        stores.add(new Store("Store 3"));
        stores.add(new Store("Store 4"));

        StoresAdapter adapter = new StoresAdapter(stores);
        storesRecyclerView.setAdapter(adapter);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        itemsButton.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, BooksActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public static class Store {
        public String name;

        public Store(String name) {
            this.name = name;
        }
    }

    public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoreViewHolder> {

        private ArrayList<Store> stores;

        public StoresAdapter(ArrayList<Store> stores) {
            this.stores = stores;
        }

        @Override
        public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_store, parent, false);
            return new StoreViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StoreViewHolder holder, int position) {
            Store store = stores.get(position);
            holder.storeNameTextView.setText(store.name);
        }

        @Override
        public int getItemCount() {
            return stores.size();
        }

        class StoreViewHolder extends RecyclerView.ViewHolder {
            TextView storeNameTextView;

            public StoreViewHolder(View itemView) {
                super(itemView);
                storeNameTextView = itemView.findViewById(R.id.storeNameTextView);
            }
        }
    }
}
