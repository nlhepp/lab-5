package com.n8.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesPage extends AppCompatActivity {
    TextView welcomeTxt;
    public static ArrayList<Note> notes = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Nate", "Started Notes Page");
        setContentView(R.layout.activity_notespage);
        ArrayList<String> displayNotes = new ArrayList();
        welcomeTxt = (TextView) findViewById(R.id.wecometext);
        SharedPreferences sharedPreferences = getSharedPreferences("com.n8.notes", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user","unkown user");
        welcomeTxt.setText("Hello "+ name);
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(
                "notes", Context.MODE_PRIVATE,null);
        DBHelper dbhelp = new DBHelper(sqLiteDatabase);
        notes = dbhelp.readNotes(name);


        for (Note note: notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getdate()));
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.noteslist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), Addanote.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Log.i("Nate", "Going home");
                returnHome();
                return true;
            case R.id.addnote:
                Log.i("Nate", "Going to add a note");
                switchToAddNote();
                return true;
            default:
                return true;
        }
    }

    public void switchToAddNote(){
        Intent intent = new Intent(this, Addanote.class);
        startActivity(intent);
    }


    public void returnHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", "");
        SharedPreferences sharedPreferences = getSharedPreferences("com.n8.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("user").apply();
        startActivity(intent);
    }

}