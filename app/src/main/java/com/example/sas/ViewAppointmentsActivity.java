package com.example.sas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sas.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentsActivity extends AppCompatActivity {

    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        appointmentList = new ArrayList<>();

        adapter = new AppointmentAdapter(appointmentList);
        recyclerView.setAdapter(adapter);

        fetchAppointments();
    }

    private void fetchAppointments() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch appointments only once using get() instead of addSnapshotListener
        db.collection("appointments")
                .whereEqualTo("userId", currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot value = task.getResult();
                        if (value == null || value.isEmpty()) {
                            Toast.makeText(ViewAppointmentsActivity.this, "No appointments found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        appointmentList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Log.d("Firestore", "Appointment Data: " + doc.getData());

                            String id = doc.getId();
                            String userId = doc.getString("userId") != null ? doc.getString("userId") : "Unknown User";
                            String clientName = doc.getString("clientName") != null ? doc.getString("clientName") : "Unknown Client";
                            String service = doc.getString("service") != null ? doc.getString("service") : "Unknown Service";
                            String date = doc.getString("appointment_date") != null ? doc.getString("appointment_date") : "No Date";
                            String time = doc.getString("appointment_time") != null ? doc.getString("appointment_time") : "No Time";
                            String status = doc.getString("status") != null ? doc.getString("status") : "Pending";

                            // Fetch the mobile number from users collection
                            final String[] mobile = new String[1];
                            mobile[0] = "No Mobile"; // Default value

                            db.collection("users").document(userId)
                                    .get()
                                    .addOnCompleteListener(userTask -> {
                                        if (userTask.isSuccessful()) {
                                            DocumentSnapshot userDoc = userTask.getResult();
                                            if (userDoc != null && userDoc.exists()) {
                                                // Fetch mobile number from the 'users' collection
                                                mobile[0] = userDoc.getString("mobile");
                                                if (mobile[0] == null) {
                                                    mobile[0] = "No Mobile"; // If mobile is null in the 'users' document
                                                }
                                            }
                                        } else {
                                            Log.e("Firestore", "Error fetching user data: ", userTask.getException());
                                        }

                                        // After fetching the mobile number, create the Appointment object
                                        Appointment appointment = new Appointment(id, userId, clientName, service, date, time, status, mobile[0]);
                                        appointmentList.add(appointment);

                                        // Notify adapter about the data changes
                                        adapter.notifyDataSetChanged();
                                    });
                        }

                    } else {
                        Log.e("Firestore", "Error fetching data: ", task.getException());
                        Toast.makeText(ViewAppointmentsActivity.this, "Error fetching data!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
