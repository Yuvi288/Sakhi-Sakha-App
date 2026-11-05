package com.example.sas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sas.models.Appointment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminViewAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminAppointmentAdapter adminAdapter;
    private List<Appointment> appointmentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_appointments);

        recyclerView = findViewById(R.id.recyclerViewAllAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        appointmentList = new ArrayList<>();

        adminAdapter = new AdminAppointmentAdapter(this, appointmentList);
        recyclerView.setAdapter(adminAdapter);

        fetchAllAppointments();
    }

    private void fetchAllAppointments() {
        db.collection("appointments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        appointmentList.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Appointment appointment = doc.toObject(Appointment.class);
                            appointment.setId(doc.getId());
                            appointmentList.add(appointment);
                        }
                        adminAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load appointments", Toast.LENGTH_SHORT).show();
                        Log.e("AdminAppointments", "Error loading appointments", task.getException());
                    }
                });
    }
}