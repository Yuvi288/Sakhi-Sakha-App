package com.example.sas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, mobileNumberEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginTextView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Find Views
        nameEditText = findViewById(R.id.nameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        // Navigate to Login Page
        loginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Register User
        registerButton.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
    }

    // Validate Input Fields
    private boolean validateInputs() {
        String name = nameEditText.getText().toString().trim();
        String mobile = mobileNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            return false;
        }
        if (mobile.isEmpty()) {
            mobileNumberEditText.setError("Mobile number is required");
            return false;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    // Register New User
    private void registerUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String mobile = mobileNumberEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserData(user.getUid(), name, mobile, email);
                        }
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            // Email already registered
                            runOnUiThread(() -> Toast.makeText(this, "Email already in use. Try logging in.", Toast.LENGTH_LONG).show());
                            Log.e(TAG, "Error: Email already exists.");
                        } else {
                            // Other errors
                            runOnUiThread(() -> Toast.makeText(this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show());
                            Log.e(TAG, "Registration Error: ", task.getException());
                        }
                    }
                });
    }

    // Save User Data in Firestore
    private void saveUserData(String userId, String name, String mobile, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("name", name);
        userData.put("mobile", mobile);
        userData.put("email", email);

        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    runOnUiThread(() -> Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show());
                    Log.d(TAG, "User data saved successfully in Firestore");

                    // Navigate to Home Page
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    runOnUiThread(() -> Toast.makeText(this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    Log.e(TAG, "Firestore Error: ", e);
                });
    }
}
