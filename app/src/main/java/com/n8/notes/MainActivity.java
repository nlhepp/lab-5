package com.n8.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("com.n8.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        if (username.length()>0){
            openNotes(username);
        } else{
            setContentView(R.layout.activity_main);
        }

    }
    public void clickFunction(View view) {
        EditText usernameT = (EditText) findViewById(R.id.usernameTxt);
        String username = usernameT.getText().toString();
        EditText passwordT = (EditText) findViewById(R.id.passwordTxt);
        String password = passwordT.getText().toString();
        sharedPreferences = getSharedPreferences("com.n8.notes", Context.MODE_PRIVATE);
        if (username.length() > 0 && password.length() > 0){
            openNotes(username);
            sharedPreferences.edit().putString("user", username).apply();
        }
    }

    public void openNotes(String username){
        Intent intent = new Intent(this, NotesPage.class);
        intent.putExtra("name", username);
        startActivity(intent);
    }



}

