package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DiscoverActivity extends AppCompatActivity {

    private LinearLayout btnHome, btnDiscovery, btnBlogs, btnProfile;
    private ImageView GotoCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        btnHome = findViewById(R.id.homebtn);
        btnDiscovery = findViewById(R.id.discoverybtn);
        btnBlogs = findViewById(R.id.blogsbtn);
        btnProfile = findViewById(R.id.profilebtn);
        GotoCart = findViewById(R.id.gotoCart);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DiscoverActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(DiscoverActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        btnDiscovery.setOnClickListener(v -> {
            Toast.makeText(DiscoverActivity.this, "You're already on Discover!", Toast.LENGTH_SHORT).show();
        });

        btnBlogs.setOnClickListener(v -> {
            Intent intent = new Intent(DiscoverActivity.this, WeatherDetails.class);
            startActivity(intent);
        });

        GotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoverActivity.this, CartActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });

        findViewById(R.id.cat_men).setOnClickListener(v -> {
            Intent i = new Intent(this, ProductListActivity.class);
            i.putExtra("title", "Men");
            i.putExtra("category", "man");
            startActivity(i);
        });
        findViewById(R.id.cat_women).setOnClickListener(v -> {
            Intent i = new Intent(this, ProductListActivity.class);
            i.putExtra("title", "Women");
            i.putExtra("category", "woman");
            startActivity(i);
        });
        findViewById(R.id.cat_kid).setOnClickListener(v -> {
            Intent i = new Intent(this, ProductListActivity.class);
            i.putExtra("title", "Kids");
            i.putExtra("category", "kid");
            startActivity(i);
        });
        findViewById(R.id.cat_sneaker).setOnClickListener(v -> {
            Intent i = new Intent(this, ProductListActivity.class);
            i.putExtra("title", "Sneakers");
            i.putExtra("category", "sneakers");
            startActivity(i);
        });
    }
}
