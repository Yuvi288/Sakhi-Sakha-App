package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Ensure this layout file exists

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Finding buttons by their IDs
        Button profileButton = findViewById(R.id.Myprofile);
        Button ManageStaffButton = findViewById(R.id.ManageStaff);
        Button manageAppointmentsButton = findViewById(R.id.manageAppointmentsButton);
        Button manageServicesButton = findViewById(R.id.manageServicesButton);
        Button academyButton = findViewById(R.id.academyButton);
        Button logoutButton = findViewById(R.id.logoutButton); // Ensure this ID exists in XML

        // Setting click listeners for each button
        manageAppointmentsButton.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AppointmentsDashboardActivity.class))
        );

        ManageStaffButton.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ManageStaff.class))
        );

        profileButton.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, Profile.class))
        );

        manageServicesButton.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ServiceSelectionActivity.class))
        );

        academyButton.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, academy.class)) // Ensure class name matches
        );

        // Logout button click listener
        logoutButton.setOnClickListener(v -> {
            auth.signOut(); // Logout from Firebase
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);
            finish(); // Close current activity
        });
    }
}
