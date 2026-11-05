package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    private ImageView profileImage;
    private TextView userName, userEmail, userPhone;
    private Button btnEditProfile, btnLogout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Find Views
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Load User Profile from Firestore
        loadUserProfile();

        // Handle Edit Profile Click
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Handle Logout Click
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(Profile.this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Profile.this, MainActivity.class)); // Go back to login page
            finish();
        });
    }

    // Fetch User Details from Firestore
    private void loadUserProfile() {
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            String phone = documentSnapshot.getString("phone");

                            userName.setText(name != null ? name : "N/A");
                            userEmail.setText(email != null ? email : "N/A");
                            userPhone.setText(phone != null ? phone : "N/A");
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Profile.this, "Failed to load profile!", Toast.LENGTH_SHORT).show()
                    );
        }
    }
}
