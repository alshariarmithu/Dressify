package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private TextView displayName, displayEmail, displayMobile, displayAddress, displayCountry;
    private Button updateInfoButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout homeclick = findViewById(R.id.homebtn);
        LinearLayout blogsclick = findViewById(R.id.blogsbtn);
        LinearLayout discoverclick = findViewById(R.id.discoverybtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // UI Elements
        displayName = findViewById(R.id.display_name);
        displayEmail = findViewById(R.id.display_email);
        displayMobile = findViewById(R.id.display_mobile);
        displayAddress = findViewById(R.id.display_address);
        displayCountry = findViewById(R.id.display_country);
        updateInfoButton = findViewById(R.id.update_info_button);

        ImageView GotoCart = findViewById(R.id.gotocart);

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Fetch user data from Firebase Realtime Database
            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);

                        // Set data to TextViews
                        displayName.setText(user.fullName != null ? user.fullName : "Please Update");
                        displayEmail.setText(user.email != null ? user.email : "Please Update");
                        displayMobile.setText(user.mobile != null ? user.mobile : "Please Update");
                        displayAddress.setText(user.address != null ? user.address : "Please Update");
                        displayCountry.setText(user.country != null ? user.country : "Please Update");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                    Toast.makeText(ProfileActivity.this,
                            "Failed to load products: " + error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Handle update button click
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to update information activity
                Intent intent = new Intent(ProfileActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });

        GotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });

        homeclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent); // Start the ProfileActivity
            }
        });
        blogsclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WeatherDetails.class);
                startActivity(intent); // Start the BlogsActivity
            }
        });

        discoverclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DiscoverActivity.class);
                startActivity(intent); // Start the DiscoverActivity
            }
        });
    }
}

