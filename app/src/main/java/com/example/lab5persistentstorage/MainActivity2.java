package com.example.lab5persistentstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView2;
    Menu menuOptions;

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //1. Display welcome message. Fetch username from SharedPreferences.
        textView2 = (TextView) findViewById(R.id.textView);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5persistentstorage", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        textView2.setText("Welcome " + username);

        //2. Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //3. Initiate the "notes" class variable using readNotes method implemented in DBHelper class. Use the
        //   username you got from SharedPreferences as a parameter to readNotes method.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        //4. create an ArrayList<String object by iterating over notes object.
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        //5. Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //6. Add onItemClickListener for Listview item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Initialize intent to take user to third activity (NoteActivity in this case)
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                //Add the position of the item that was clicked on as "noteid"
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        menuOptions = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5persistentstorage", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(MainActivity.usernameKeyC).apply();
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.addnote){
            //Go to activity 3
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}