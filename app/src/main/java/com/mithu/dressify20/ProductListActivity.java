package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductAdapter productAdapter;
    private RecyclerView productsRecyclerView;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        String cat = getIntent().getStringExtra("category");
        String title = getIntent().getStringExtra("title");

        setTitle(title);

        // Initialize RecyclerView
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Adapter
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        productsRecyclerView.setAdapter(productAdapter);

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference().child("products");

        // Fetch products
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Product product = childSnapshot.getValue(Product.class);

                    if (product != null && cat.equals(product.getCategory())) {
                        productList.add(product);
                    }
                }
                productAdapter.updateProducts(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(ProductListActivity.this,
                        "Failed to load products: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
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