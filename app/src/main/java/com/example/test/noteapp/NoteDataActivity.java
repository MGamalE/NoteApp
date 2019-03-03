package com.example.test.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteDataActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.test.noteapp.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.test.noteapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.test.noteapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.example.test.noteapp.EXTRA_DATE";


    EditText noteTitle, noteDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_data);

        noteTitle = findViewById(R.id.note_title);
        noteDesc = findViewById(R.id.note_desc);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            noteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            noteDesc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Note");
        }
    }


    private void saveNote() {
        String title = noteTitle.getText().toString();
        String description = noteDesc.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM/dd");
        String currentDate = simpleDateFormat.format(calendar.getTime());

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE, currentDate);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
