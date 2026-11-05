package com.example.sas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sas.models.Appointment;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.clientNameTextView.setText("Client: " + appointment.getClientName());
        holder.serviceTextView.setText("Service: " + appointment.getService());
        holder.dateTextView.setText("Date: " + appointment.getAppointment_date());
        holder.timeTextView.setText("Time: " + appointment.getAppointment_time());

        // Set the mobile number
        holder.mobileTextView.setText("Mobile: " + appointment.getMobile());

        // Set status text
        String status = appointment.getStatus();
        holder.statusTextView.setText("Status: " + status);

        // Color coding based on status
        switch (status.toLowerCase()) {
            case "accepted":
                holder.statusTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_green_dark));
                break;
            case "rejected":
                holder.statusTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_red_dark));
                break;
            default: // Pending or unknown
                holder.statusTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_orange_dark));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientNameTextView, serviceTextView, dateTextView, timeTextView, statusTextView, mobileTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView); // Status view
            mobileTextView = itemView.findViewById(R.id.mobileTextView); // Mobile view
        }
    }
}
