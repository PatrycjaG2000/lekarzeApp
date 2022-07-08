package com.example.booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Services extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }
}