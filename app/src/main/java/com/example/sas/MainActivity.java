package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton, registerButton;
    ProgressBar loginProgress;
    boolean isPasswordVisible = false;

    FirebaseAuth auth;
    FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // UI components
        emailEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginProgress = findViewById(R.id.loginprogress);

        // Check if user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            checkUserRole(currentUser.getUid()); // Check Role & Redirect
        }

        // Login Button Click
        loginButton.setOnClickListener(v -> {
            if (validateInputs()) {
                loginUser();
            }
        });

        // Register Button Click → Open RegistrationActivity
        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        });
    }

    // Function to Validate Inputs
    private boolean validateInputs() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required!");
            return false;
        }
        return true;
    }

    // Function to Perform Firebase Login
    private void loginUser() {
        loginProgress.setVisibility(View.VISIBLE);

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    loginProgress.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            checkUserRole(user.getUid()); // Check Role & Redirect
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Function to Check User Role in Firestore
    private void checkUserRole(String userId) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");

                        // ✅ Log Firestore role output
                        if (role != null) {
                            Log.d("FirebaseDebug", "Fetched Role: " + role);
                        } else {
                            Log.d("FirebaseDebug", "Role is NULL");
                        }

                        // ✅ Ensure role check works correctly
                        if ("admin".equalsIgnoreCase(role)) {
                            Log.d("FirebaseDebug", "Redirecting to Admin Page");
                            startActivity(new Intent(MainActivity.this, AdminDashboardActivity.class));
                        } else {
                            Log.d("FirebaseDebug", "Redirecting to Home Page");
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }
                        finish();
                    } else {
                        Log.d("FirebaseDebug", "No such user document in Firestore");
                        Toast.makeText(MainActivity.this, "User data not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDebug", "Error fetching role: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Error fetching role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}