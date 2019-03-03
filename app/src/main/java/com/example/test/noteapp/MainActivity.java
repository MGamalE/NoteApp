package com.example.test.noteapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    private NoteViewModel noteViewModel;
    private NoteItemAdapter noteItemAdapter;
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        relativeLayout = findViewById(R.id.empty_notes);


        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);
        noteItemAdapter = new NoteItemAdapter();
        recyclerView.setAdapter(noteItemAdapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //update RecyclerView
                noteItemAdapter.setNotes(notes);
            }
        });
        if (noteItemAdapter.getListSize() == 0) {
            recyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteItemAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                size = noteItemAdapter.getListSize() - 1;
                Log.e("sosss", String.valueOf(size));

                if (size == 0) {
                    recyclerView.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        noteItemAdapter.setOnItemClickListener(new NoteItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, NoteDataActivity.class);
                intent.putExtra(NoteDataActivity.EXTRA_ID, note.getId());
                intent.putExtra(NoteDataActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(NoteDataActivity.EXTRA_DESCRIPTION, note.getDescription());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }


    public void addNote(View view) {
        Intent intent = new Intent(this, NoteDataActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NoteDataActivity.EXTRA_TITLE);
            String description = data.getStringExtra(NoteDataActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(NoteDataActivity.EXTRA_DATE);

            Note note = new Note(title, description, date);
            noteViewModel.insert(note);
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NoteDataActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(NoteDataActivity.EXTRA_TITLE);
            String description = data.getStringExtra(NoteDataActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(NoteDataActivity.EXTRA_DATE);

            Note note = new Note(title, description, date);
            note.setId(id);
            noteViewModel.update(note);
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
        } else {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                size = noteItemAdapter.getListSize();
                if (size != 0) {
                    noteViewModel.deleteAllNotes();
                    recyclerView.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    size = 0;
                    Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
