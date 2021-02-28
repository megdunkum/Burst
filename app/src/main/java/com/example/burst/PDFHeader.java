package com.example.burst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class PDFHeader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfheader);
    }

    public void start() {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }
}