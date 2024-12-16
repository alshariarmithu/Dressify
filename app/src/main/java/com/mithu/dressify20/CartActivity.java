package com.mithu.dressify20;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView subtotalAmount, deliveryAmount, totalAmount, emptyCartTextView;
    private Button checkoutButton;

    private CartAdapter cartAdapter;
    private ArrayList<CartProduct> cartItems = new ArrayList<>();
    private DatabaseReference cartRef;

    private double subtotal = 0.0;
    private double deliveryFee = 0.00; // Fixed delivery fee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        subtotalAmount = findViewById(R.id.subtotalAmount);
        deliveryAmount = findViewById(R.id.deliveryAmount);
        totalAmount = findViewById(R.id.totalAmount);
        checkoutButton = findViewById(R.id.checkoutButton);
        emptyCartTextView = findViewById(R.id.emptyCartTextView); // Initialize the empty cart message

        // Set up RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        // Firebase Reference
        String userId = FirebaseAuth.getInstance().getUid(); // Get the user ID
        cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId).child("items");

        // Fetch Cart Items
        fetchCartItems();

        // Checkout Button Click
        checkoutButton.setOnClickListener(v -> {
            // Proceed with checkout
            checkout();
        });
    }

    private void fetchCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                subtotal = 0.0;

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CartProduct product = itemSnapshot.getValue(CartProduct.class);
                    subtotal += product.getPrice() * product.getQuantity();
                    cartItems.add(product);
                }
                cartAdapter.notifyDataSetChanged();
                updateSummary();

                // Show "Empty" if the cart is empty
                if (cartItems.isEmpty()) {
                    emptyCartTextView.setVisibility(TextView.VISIBLE);
                    cartRecyclerView.setVisibility(RecyclerView.GONE);
                } else {
                    emptyCartTextView.setVisibility(TextView.GONE);
                    cartRecyclerView.setVisibility(RecyclerView.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "Failed to load cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSummary() {
        subtotalAmount.setText(String.format("$%.2f", subtotal));
        deliveryAmount.setText(String.format("$%.2f", deliveryFee));
        totalAmount.setText(String.format("$%.2f", subtotal + deliveryFee));
    }

    // Checkout logic to save cart items to Firebase and update the total price
    private void checkout() {
        // Create a HashMap to represent the order
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders").child(userId).push();

        // Prepare order details
        HashMap<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("totalAmount", subtotal + deliveryFee);

        // Create a HashMap for cart items
        HashMap<String, Object> items = new HashMap<>();
        for (CartProduct product : cartItems) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("productId", product.getId());
            item.put("productName", product.getName());
            item.put("price", product.getPrice());
            item.put("quantity", product.getQuantity());
            items.put(product.getId(), item); // Store each product by its ID
        }

        orderDetails.put("items", items);

        // Save the order to Firebase
        orderRef.setValue(orderDetails).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(CartActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                // Optionally, you can clear the cart after placing the order
                cartRef.removeValue();

                // Navigate to a new screen (e.g., Order Confirmation screen)
                // Intent intent = new Intent(CartActivity.this, OrderConfirmationActivity.class);
                // startActivity(intent);
                finish();  // Close the CartActivity
            } else {
                Toast.makeText(CartActivity.this, "Failed to place order: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
