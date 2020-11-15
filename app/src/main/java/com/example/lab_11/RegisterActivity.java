package com.example.lab_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    CheckBox checkStudent, checkTeacher;
    Button registerBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameReg);
        passwordInput = findViewById(R.id.passwordReg);
        checkTeacher = findViewById(R.id.teacherCheck);
        checkStudent = findViewById(R.id.studentCheck);
        registerBtn = findViewById(R.id.regBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = usernameInput.getText().toString();
                String passwordValue = passwordInput.getText().toString();
                String type;
                System.out.println(usernameValue);

                if(checkTeacher.isChecked()) {
                    type = "teacher";
                } else if (checkStudent.isChecked()) {
                    type = "student";
                } else {
                    type = "not_given";
                }

                dbHelper = new DBHelper(getApplicationContext());
                boolean isInserted = dbHelper.insertUsers(usernameValue, passwordValue, type);

                if(isInserted) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "User added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "User not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}