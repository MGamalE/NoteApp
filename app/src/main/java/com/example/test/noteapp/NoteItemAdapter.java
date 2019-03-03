package com.example.test.noteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.ViewHolder> {

    Context context;
    ArrayList<String> titleArr;
    ArrayList<String> descArr;
    List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;


//    public NoteItemAdapter(Context context, ArrayList<Note> notes) {
//        this.context=context;
//        this.notes=notes;
//
//
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewHolder = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);


        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.noteTitle.setText(notes.get(i).getTitle());
        viewHolder.noteDesc.setText(notes.get(i).getDescription());
        viewHolder.noteDate.setText(notes.get(i).getDate());
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public int getListSize() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteDesc, noteDate;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteDesc = itemView.findViewById(R.id.note_desc);
            noteDate = itemView.findViewById(R.id.note_date);
            cardView = itemView.findViewById(R.id.card_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
