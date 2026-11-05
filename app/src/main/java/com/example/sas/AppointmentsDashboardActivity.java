package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sas.models.Appointment;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_dashboard);



        // ✅ Button for New Appointment
        MaterialButton newAppointmentBtn = findViewById(R.id.newAppointmentButton);
        if (newAppointmentBtn != null) {
            newAppointmentBtn.setOnClickListener(view -> {
                Intent intent = new Intent(this, ManageAppointmentsActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("AppointmentsDashboard", "newAppointmentButton not found!");
        }

        // ✅ Button for View Appointments
        MaterialButton viewAppointmentsBtn = findViewById(R.id.viewAppointmentsButton);
        if (viewAppointmentsBtn != null) {
            viewAppointmentsBtn.setOnClickListener(view -> {
                Intent intent = new Intent(this, ViewAppointmentsActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("AppointmentsDashboard", "viewAppointmentsButton not found!");
        }
    }

    private void fetchAppointments() {
        db.collection("appointments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        appointmentList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Appointment appointment = document.toObject(Appointment.class);
                            appointmentList.add(appointment);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error fetching appointments", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error: ", task.getException());
                    }
                });
    }
}
