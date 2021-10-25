package com.example.lab5persistentstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    public void onClickSave(View view){
        //1. Get editText view and the content that user entered.
        EditText editText = (EditText) findViewById(R.id.giantEditText);
        String content = editText.getText().toString();
        //2. Initialize SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        //3. Initialize DBHelper class.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        //4. Set username in the following variable by fetching it from sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5persistentstorage", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        //5. Save information to database.
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1){
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else{
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", sharedPreferences.getString("username", ""));
        startActivity(intent);

    }

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //1. Get EditText view.
        EditText giantEditText = (EditText) findViewById(R.id.giantEditText);
        //2. Get Intent.
        Intent mIntent = getIntent();
        //3. Get the value of integer "noteid" from intent.
        int intNote = mIntent.getIntExtra("noteid",-1);
        noteid = intNote;
        //4. Intialize class variable "noteid" with the value from intent.
        if (noteid != -1){
            //Display content of note by retrieving "notes" ArrayList in SecondActivity
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            //Use editText.setText() to display the contents of this note on screen.
            giantEditText.setText(noteContent);
        }
    }
}