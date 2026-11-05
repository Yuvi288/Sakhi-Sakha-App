package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView welcomeText;
    private String fullText = "Welcome to Sakhi and Sakha\nStudio And Academy!";
    private int index = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Find Welcome TextView
        welcomeText = findViewById(R.id.welcomeText);

        // Start Typing Animation
        startTypingAnimation();

        // After 3 seconds, move to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3500); // Slightly increase delay for long text
    }

    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < fullText.length()) {
                    welcomeText.setText(fullText.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 50); // Delay between letters (100ms for smoother effect)
                }
            }
        }, 100);
    }
}
