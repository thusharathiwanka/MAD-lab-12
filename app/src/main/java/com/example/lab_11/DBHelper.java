package com.example.lab_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "CourseAppDB";
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (user_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT, type TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS message (message_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, subject TEXT, message TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS message");
    }

    public boolean insertUsers(String username, String password, String type) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", username);
        contentValues.put("PASSWORD", password);
        contentValues.put("TYPE", type);

        long result = sqLiteDatabase.insert("user", null, contentValues);
        sqLiteDatabase.close();

        return result != -1;
    }

    public Cursor checkUser(String username, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE name = ? AND password = ?", new String[] {username, password});

        return cursor;
    }

    public boolean insertMessage(String username, String subject, String message) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER", username);
        contentValues.put("SUBJECT", subject);
        contentValues.put("MESSAGE", message);

        long result = sqLiteDatabase.insert("message", null, contentValues);

        return result != -1;
    }

    public Cursor getAllMessages() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM message", null);

        return cursor;
    }

    public Cursor getMessage(String message) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT subject FROM message WHERE message = ?", new String[] {message});

        return cursor;
    }

    public Cursor getNewMessage() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM message ORDER BY message_id DESC LIMIT 1", null);

        return cursor;
    }
}
