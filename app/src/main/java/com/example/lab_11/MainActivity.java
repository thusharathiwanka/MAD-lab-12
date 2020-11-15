package com.example.lab_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button loginBtn, regBtn;
    EditText usernameInput, passwordInput;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regBtn = findViewById(R.id.regBtnLog);
        loginBtn = findViewById(R.id.logBtnLog);
        usernameInput = findViewById(R.id.usernameLog);
        passwordInput = findViewById(R.id.passwordLog);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = usernameInput.getText().toString();
                String passwordValue = passwordInput.getText().toString();

                dbHelper = new DBHelper(getApplicationContext());
                Cursor cursor = dbHelper.checkUser(usernameValue, passwordValue);

                if(cursor.getCount() < 1) {
                    Toast.makeText(MainActivity.this, "Username or password is invalid", Toast.LENGTH_SHORT).show();
                } else {
                    String type = "";
                    while (cursor.moveToNext()) {
                        type = cursor.getString(3);
                    }

                    Intent intent;
                    if(type.equals("student")) {
                        intent = new Intent(getApplicationContext(), StudentActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), TeacherActivity.class);
                    }

                    intent.putExtra("USERNAME", usernameValue);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}