package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);

        Button maleServicesButton = findViewById(R.id.maleServicesButton);
        Button femaleServicesButton = findViewById(R.id.femaleServicesButton);

        // Navigate to Male Services Page
        maleServicesButton.setOnClickListener(v ->
                startActivity(new Intent(ServiceSelectionActivity.this, MaleServicesActivity.class))
        );

        // Navigate to Female Services Page (You already have this page)
        femaleServicesButton.setOnClickListener(v ->
                startActivity(new Intent(ServiceSelectionActivity.this, ManageServicesActivity.class))
        );
    }
}
