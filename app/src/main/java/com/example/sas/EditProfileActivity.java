package com.example.sas;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPhone;
    private Button btnSaveChanges;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Find Views
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        progressBar = findViewById(R.id.progressBar);

        // Load Existing User Data
        loadUserProfile();

        // Save Changes Click Listener
        btnSaveChanges.setOnClickListener(v -> saveProfileChanges());
    }

    // Fetch User Data from Firestore
    private void loadUserProfile() {
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            editName.setText(documentSnapshot.getString("name"));
                            editEmail.setText(documentSnapshot.getString("email"));
                            editPhone.setText(documentSnapshot.getString("phone"));
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(EditProfileActivity.this, "Failed to load profile!", Toast.LENGTH_SHORT).show()
                    );
        }
    }

    // Save Profile Changes to Firestore
    private void saveProfileChanges() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", name);
        userUpdates.put("email", email);
        userUpdates.put("phone", phone);

        db.collection("users").document(currentUser.getUid())
                .update(userUpdates)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to Profile Page
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                });
    }
}
