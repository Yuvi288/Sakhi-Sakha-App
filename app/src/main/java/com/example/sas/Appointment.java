package com.example.sas.models;

public class Appointment {
    private String id; // Firestore Document ID
    private String userId;
    private String clientName;
    private String service;
    private String appointment_date;
    private String appointment_time;
    private String status; // ✅ Added status field
    private String mobile; // Added mobile field

    // ✅ Default Constructor (Required for Firestore)
    public Appointment() {}

    // ✅ Full Constructor including status and mobile
    public Appointment(String id, String userId, String clientName, String service, String appointment_date, String appointment_time, String status, String mobile) {
        this.id = id;
        this.userId = userId;
        this.clientName = clientName;
        this.service = service;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.status = status;
        this.mobile = mobile; // Initialize mobile number
    }

    // ✅ Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getAppointment_date() { return appointment_date; }
    public void setAppointment_date(String appointment_date) { this.appointment_date = appointment_date; }

    public String getAppointment_time() { return appointment_time; }
    public void setAppointment_time(String appointment_time) { this.appointment_time = appointment_time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMobile() { return mobile; } // Getter for mobile
    public void setMobile(String mobile) { this.mobile = mobile; } // Setter for mobile
}
