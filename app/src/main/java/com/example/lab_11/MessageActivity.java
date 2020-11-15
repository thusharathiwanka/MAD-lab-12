package com.example.lab_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {
    EditText message;
    TextView subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        subject = findViewById(R.id.subjectText);
        message = findViewById(R.id.message);

        Intent intent = getIntent();
        String subjectValue = intent.getStringExtra("SUBJECT");
        String messageValue = intent.getStringExtra("MESSAGE");

        subject.setText(subjectValue);
        message.setText(messageValue);
    }
}