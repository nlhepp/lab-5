package com.n8.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Addanote extends AppCompatActivity {
    int noteid = -1;
    EditText typenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addanote);
        Intent intent= getIntent();
        noteid = intent.getIntExtra("noteid", -1);
        typenote = findViewById(R.id.typenotetxt);
        Log.d("Nate", "Here"+noteid);
        if (noteid != -1){
            Note note = NotesPage.notes.get(noteid);
            String noteContent = note.getContent();
            typenote.setText(noteContent);
        }

    }


    public void saveNote(View view){
        String content = typenote.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(
                "notes", Context.MODE_PRIVATE,null);
        DBHelper dbhelp = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedprefs = getSharedPreferences("com.n8.notes", Context.MODE_PRIVATE);
        String username = sharedprefs.getString("user", "user");
        String title;
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateformat.format(new Date());
        if (noteid == -1){
            title = "NOTE_"+ (NotesPage.notes.size() +1);
            dbhelp.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid +1);
            dbhelp.updateNote(title, date, content, username);
        }
        Intent intent = new Intent(this, NotesPage.class);
        startActivity(intent);
        return;
    }
}