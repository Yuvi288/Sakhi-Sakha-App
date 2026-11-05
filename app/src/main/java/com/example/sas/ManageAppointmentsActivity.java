package com.example.sas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ManageAppointmentsActivity extends AppCompatActivity {

    // UI components
    private EditText appointmentClient, appointmentMobile, appointmentDate;
    private Spinner appointmentTimeSpinner, appointmentServiceSpinner;
    private Button saveAppointmentButton, clearFormButton, viewAppointmentsButton;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private Button paymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointments);

        paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(v ->
                startActivity(new Intent(ManageAppointmentsActivity.this, PaymentImageActivity.class))
        );

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Find Views
        appointmentClient = findViewById(R.id.appointmentClient);
        appointmentMobile = findViewById(R.id.appointmentMobile); // Added for mobile number
        appointmentDate = findViewById(R.id.appointmentDate);
        appointmentTimeSpinner = findViewById(R.id.appointmentTimeSpinner);
        appointmentServiceSpinner = findViewById(R.id.appointmentServiceSpinner);
        saveAppointmentButton = findViewById(R.id.saveAppointmentButton);
        clearFormButton = findViewById(R.id.clearFormButton);
        viewAppointmentsButton = findViewById(R.id.viewAppointmentsButton);

        // Populate dropdowns
        populateTimeSlots();
        populateServiceDropdown();

        // Auto-fill client name and mobile number
        fetchClientDetailsFromFirestore();

        // Date picker
        appointmentDate.setOnClickListener(v -> showDatePicker());

        // Save appointment
        saveAppointmentButton.setOnClickListener(v -> saveAppointment());

        // Clear form
        clearFormButton.setOnClickListener(v -> clearForm());

        // Navigate to View Appointments
        viewAppointmentsButton.setOnClickListener(v ->
                startActivity(new Intent(ManageAppointmentsActivity.this, ViewAppointmentsActivity.class))
        );
    }

    // 🔹 Fetch user name and mobile number from Firestore and set to EditText
    private void fetchClientDetailsFromFirestore() {
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name"); // ✅ use "name" for consistency
                            String mobile = documentSnapshot.getString("mobile"); // Get mobile number
                            if (name != null && !name.isEmpty()) {
                                appointmentClient.setText(name);
                                appointmentClient.setEnabled(false);
                                appointmentClient.setFocusable(false);
                                appointmentClient.setClickable(false);
                            } else {
                                appointmentClient.setText("Name not available");
                            }
                            if (mobile != null && !mobile.isEmpty()) {
                                appointmentMobile.setText(mobile); // Set mobile number
                                appointmentMobile.setEnabled(false);
                                appointmentMobile.setFocusable(false);
                                appointmentMobile.setClickable(false);
                            } else {
                                appointmentMobile.setText("Mobile not available");
                            }
                        } else {
                            Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error fetching name", Toast.LENGTH_SHORT).show()
                    );
        }
    }

    // 🔹 Show Date Picker Dialog
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Create a Calendar instance for the selected date
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, month1, dayOfMonth);

                    // Format the date using SimpleDateFormat
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // Set the formatted date to the EditText
                    appointmentDate.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    // 🔹 Save appointment in Firestore
    private void saveAppointment() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String clientName = appointmentClient.getText().toString().trim();
        String clientMobile = appointmentMobile.getText().toString().trim(); // Get mobile number
        String selectedDate = appointmentDate.getText().toString().trim();
        String selectedTime = appointmentTimeSpinner.getSelectedItem().toString();
        String selectedService = appointmentServiceSpinner.getSelectedItem().toString();

        if (clientName.isEmpty()) {
            Toast.makeText(this, "Client name is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clientMobile.isEmpty()) {
            Toast.makeText(this, "Mobile number is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> appointment = new HashMap<>();
        appointment.put("userId", currentUser.getUid());
        appointment.put("clientName", clientName);
        appointment.put("mobile", clientMobile); // Add mobile number
        appointment.put("service", selectedService);
        appointment.put("appointment_date", selectedDate);
        appointment.put("appointment_time", selectedTime);
        appointment.put("status", "Pending");
        appointment.put("timestamp", Timestamp.now());

        db.collection("appointments")
                .add(appointment)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(ManageAppointmentsActivity.this, "Appointment Saved!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(ManageAppointmentsActivity.this, "Failed to save!", Toast.LENGTH_LONG).show()
                );
    }

    // 🔹 Clear form inputs
    private void clearForm() {
        appointmentDate.setText("");
        appointmentTimeSpinner.setSelection(0);
        appointmentServiceSpinner.setSelection(0);
        appointmentMobile.setText(""); // Clear mobile number
        Toast.makeText(this, "Form cleared!", Toast.LENGTH_SHORT).show();
    }

    // 🔹 Populate time slots
    private void populateTimeSlots() {
        String[] timeSlots = {"09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM",
                "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM", "05:00 PM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        appointmentTimeSpinner.setAdapter(adapter);
    }

    // 🔹 Populate services
    private void populateServiceDropdown() {
        String[] services = {"Haircut", "Hair Coloring", "Facial", "Manicure", "Pedicure", "Makeup", "Bridal Package", "Waxing"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, services);
        appointmentServiceSpinner.setAdapter(adapter);
    }
}
