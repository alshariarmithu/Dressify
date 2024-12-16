package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mithu.dressify20.ProductAdapter;
import com.mithu.dressify20.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductAdapter productAdapter;
    private RecyclerView productsRecyclerView;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView GotoCart = findViewById(R.id.gotocart);
        Button shopnow = findViewById(R.id.showNow);
        LinearLayout profileclick = findViewById(R.id.profilebtn);
        LinearLayout blogsclick = findViewById(R.id.blogsbtn);
        LinearLayout discoverclick = findViewById(R.id.discoverybtn);

        // Initialize RecyclerView
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Adapter
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        productsRecyclerView.setAdapter(productAdapter);

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference().child("products");

        GotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });

        shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DiscoverActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });

        findViewById(R.id.banner).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DiscoverActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bell).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BlogActivity.class);
            startActivity(intent);
        });

        // Fetch products
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Product product = childSnapshot.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                productAdapter.updateProducts(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(HomeActivity.this,
                        "Failed to load products: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        profileclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });
        blogsclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WeatherDetails.class);
                startActivity(intent); // Start the BlogsActivity
            }
        });

        discoverclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DiscoverActivity.class);
                startActivity(intent); // Start the DiscoverActivity
            }
        });

    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("product", product); // Pass the Product object
        startActivity(intent);
    }


}