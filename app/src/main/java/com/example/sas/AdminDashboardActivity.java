package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth; // ✅ Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        auth = FirebaseAuth.getInstance(); // ✅ Initialize FirebaseAuth

        // 🔘 View Appointments Button
        Button viewAppointmentsButton = findViewById(R.id.viewAppointmentsButton);
        viewAppointmentsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminViewAppointmentsActivity.class);
            startActivity(intent);
        });

        // 🔴 Logout Button
        Button logoutButton = findViewById(R.id.logoutButto);
        logoutButton.setOnClickListener(v -> {
            auth.signOut(); // ✅ Logout from Firebase
            Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // ✅ Close current activity
        });
    }
}
