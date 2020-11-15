package com.example.lab_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {
    TextView greeting;
    EditText subject, message;
    Button sendBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        greeting = findViewById(R.id.welcomeTextTeacher);
        subject = findViewById(R.id.subjectInput);
        message = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.sendBtn);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        greeting.append(" " + username);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(getApplicationContext());
                boolean isInserted =  dbHelper.insertMessage(username, subject.getText().toString(), message.getText().toString());

                if(isInserted) {
                    Toast.makeText(TeacherActivity.this, "Message added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherActivity.this, "Message not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}