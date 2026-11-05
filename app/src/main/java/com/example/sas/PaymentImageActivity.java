package com.example.sas;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_image);

        ImageView imageView = findViewById(R.id.fullscreenImage);
        imageView.setImageResource(R.drawable.ppp ); // 🔁 your image from drawable
    }
}
