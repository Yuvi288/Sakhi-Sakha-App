package com.example.sas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sas.models.Appointment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminAppointmentAdapter extends RecyclerView.Adapter<AdminAppointmentAdapter.ViewHolder> {
    private List<Appointment> appointmentList;
    private Context context;

    public AdminAppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.clientNameTextView.setText("Client: " + appointment.getClientName());
        holder.serviceTextView.setText("Service: " + appointment.getService());
        holder.dateTextView.setText("Date: " + appointment.getAppointment_date());
        holder.timeTextView.setText("Time: " + appointment.getAppointment_time());
        holder.statusTextView.setText("Status: " + appointment.getStatus());

        // 🔹 Set dynamic status color
        switch (appointment.getStatus()) {
            case "Accepted":
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                break;
            case "Rejected":
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                break;
            case "Pending":
            default:
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
                break;
        }

        // 🔹 Set button actions
        holder.acceptButton.setOnClickListener(v -> updateStatus(appointment, "Accepted"));
        holder.rejectButton.setOnClickListener(v -> updateStatus(appointment, "Rejected"));
    }

    private void updateStatus(Appointment appointment, String newStatus) {
        FirebaseFirestore.getInstance()
                .collection("appointments")
                .document(appointment.getId())
                .update("status", newStatus)
                .addOnSuccessListener(aVoid -> {
                    appointment.setStatus(newStatus);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientNameTextView, serviceTextView, dateTextView, timeTextView, statusTextView;
        Button acceptButton, rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            acceptButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }
    }
}
