package com.example.sas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageServicesActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        TextView txtHair;
        txtHair = findViewById(R.id.txthaircut);
        txtHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1;
                i1 = new Intent(ManageServicesActivity.this,Haircut.class);
                startActivity(i1);

            }
        });

        TextView hairspa;
        hairspa = findViewById(R.id.hairspa);
        hairspa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2;
                i2 = new Intent(ManageServicesActivity.this,hairspa.class);
                startActivity(i2);
            }
        });
        ImageView haircut;
        haircut = findViewById(R.id.img1);
        haircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
                i= new Intent(ManageServicesActivity.this,Haircut.class);
                startActivity(i);
            }
        });
    }
}