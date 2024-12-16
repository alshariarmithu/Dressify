package com.mithu.dressify20;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateInfoActivity extends AppCompatActivity {

    private EditText editName, editEmail, editMobile, editAddress, editCountry;
    private Button updateInfoButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // UI Elements
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editMobile = findViewById(R.id.edit_mobile);
        editAddress = findViewById(R.id.edit_address);
        editCountry = findViewById(R.id.edit_country);
        updateInfoButton = findViewById(R.id.update_info_button);

        // Pre-fill existing user data
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // Assuming data is already fetched from Firebase in ProfileActivity, you can update here if needed
            // For now, we will leave the fields editable for user input
        }

        // Handle update button click
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate user inputs
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String mobile = editMobile.getText().toString().trim();
                String address = editAddress.getText().toString().trim();
                String country = editCountry.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || address.isEmpty() || country.isEmpty()) {
                    Toast.makeText(UpdateInfoActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update data in Firebase Realtime Database
                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    // Create User object with updated information
                    User updatedUser = new User(name, email, mobile, address, country);

                    // Save updated user info to Firebase
                    mDatabase.child(userId).setValue(updatedUser)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateInfoActivity.this, "Information updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpdateInfoActivity.this, "Failed to update information", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
