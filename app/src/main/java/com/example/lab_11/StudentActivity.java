package com.example.lab_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    TextView greeting;
    ListView messageList;
    ArrayList<String> messageNameList;
    ArrayAdapter<String> adapterMessages;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        greeting = findViewById(R.id.welcomeTextStudent);
        messageList = findViewById(R.id.messageList);

        messageNameList = new ArrayList<>();
        viewMessages();

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        greeting.append(" " + username);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = messageList.getItemAtPosition(i).toString();
                Cursor cursor = dbHelper.getMessage(message);

                if(cursor.getCount() == 0) {
                    Toast.makeText(StudentActivity.this, "There are no data for this item", Toast.LENGTH_SHORT).show();
                } else {
                    String subject = "";
                    while (cursor.moveToNext()) {
                        subject = cursor.getString(0);
                    }

                    Intent intent1 = new Intent(getApplicationContext(), MessageActivity.class);
                    intent1.putExtra("SUBJECT", subject);
                    intent1.putExtra("MESSAGE", message);
                    startActivity(intent1);
                }
            }
        });

        String CHANNEL_ID = "0";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.new_message);
            String description = getString(R.string.new_message_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Cursor cursor = dbHelper.getNewMessage();
        String newSubject = "";
        String newMessage = "";

        if(cursor.getCount() < 1) {
            Toast.makeText(getApplicationContext(), "Username or password is invalid", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                newSubject = cursor.getString(2);
                newMessage = cursor.getString(3);
            }
        }

        Intent intent1 = new Intent(this, MessageActivity.class);
        intent1.putExtra("SUBJECT", newSubject);
        intent1.putExtra("MESSAGE", newMessage);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent1);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("The Course App")
                .setContentText("You have a new Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, builder.build());
    }

    private void viewMessages() {
        dbHelper = new DBHelper(getApplicationContext());
        Cursor cursor = dbHelper.getAllMessages();

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "There are no messages", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                messageNameList.add(cursor.getString(3));
            }
        }

        adapterMessages = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageNameList);
        messageList.setAdapter(adapterMessages);
    }
}