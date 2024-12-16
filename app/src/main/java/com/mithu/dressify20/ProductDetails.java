package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {

    private Button btnBuy;
    private ImageView clothOneImage;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mAuth = FirebaseAuth.getInstance();
        ImageView backButton = findViewById(R.id.backButton);

        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            ImageView productImage = findViewById(R.id.productImage);
            TextView productTitle = findViewById(R.id.productTitle);
            TextView productbrand = findViewById(R.id.productBrand);
            TextView productDescription = findViewById(R.id.productDescription);
            TextView productPriceTxt = findViewById(R.id.productPrice);

            Button buy = findViewById(R.id.buyButton);
            buy.setOnClickListener(v -> {
                // Get Firebase Database reference
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference cartRef = database.getReference("carts").child(mAuth.getUid()).child("items");

                // Prepare the product data
                String productId = product.getId();
                String productName = product.getName();
                String productDescrip = product.getDescription();
                String productBrand = product.getBrand();
                double productPrice = product.getPrice();
                String productImageUrl = product.getImageUrl();

                // Create a map to hold product details
                HashMap<String, Object> productData = new HashMap<>();
                productData.put("id", productId);
                productData.put("name", productName);
                productData.put("brand", productBrand);
                productData.put("price", productPrice);
                productData.put("imageUrl", productImageUrl);

                // Add product to cart, or increase quantity if it already exists
                cartRef.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Product already exists, increase quantity
                            Long currentQuantity = snapshot.child("quantity").getValue(Long.class);
                            if (currentQuantity == null) {
                                currentQuantity = 0L;
                            }
                            cartRef.child(productId).child("quantity").setValue(currentQuantity + 1);
                        } else {
                            // Product does not exist, add it with quantity = 1
                            productData.put("quantity", 1);
                            cartRef.child(productId).setValue(productData);
                        }
                        Toast.makeText(v.getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(v.getContext(), "Failed to add to cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });


            // Load the product image
            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.cloths1)
                    .into(productImage);

            // Set product details
            productTitle.setText(product.getName());
            productDescription.setText(product.getDescription());
            productbrand.setText("Brand: " + product.getBrand());
            productPriceTxt.setText(String.format("Price: $%.2f", product.getPrice()));
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity to go back to the previous one
                finish();
            }
        });
    }

}
