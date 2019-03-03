package com.example.test.noteapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDataAccessObject noteDataAccessObject;
    private LiveData<List<Note>> notesList;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDataAccessObject = noteDatabase.noteDataAccessObject();
        notesList = noteDataAccessObject.getAllNotes();
    }


    public void insert(Note note) {
        new InsertAsyncTask(noteDataAccessObject).execute(note);
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDataAccessObject).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDataAccessObject).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllAsyncTask(noteDataAccessObject).execute();
    }

    public LiveData<List<Note>> getNotesList() {
        return notesList;
    }


    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDataAccessObject noteDataAccessObject;

        public InsertAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDataAccessObject noteDataAccessObject;

        public UpdateAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDataAccessObject noteDataAccessObject;

        public DeleteAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDataAccessObject.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDataAccessObject noteDataAccessObject;

        public DeleteAllAsyncTask(NoteDataAccessObject noteDataAccessObject) {
            this.noteDataAccessObject = noteDataAccessObject;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDataAccessObject.deleteAllNotes();
            return null;
        }
    }


}
