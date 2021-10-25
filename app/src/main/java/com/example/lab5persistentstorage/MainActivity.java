package com.example.lab5persistentstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String usernameKeyC;

    public void onButtonClick(View view){
        EditText myTextField = (EditText) findViewById(R.id.email);
        String str = myTextField.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5persistentstorage", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();
        goToActivity2(str);
    }

    public void goToActivity2(String s){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        usernameKeyC = usernameKey;
        
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5persistentstorage", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            String name = sharedPreferences.getString(usernameKey, "");
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("message", sharedPreferences.getString("username", ""));
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

}