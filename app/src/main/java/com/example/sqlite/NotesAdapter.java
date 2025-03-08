package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    // khai bao bien toan cuc
    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;


    public NotesAdapter(MainActivity context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Tao ViewHolder
    private static class ViewHolder {
        TextView textViewNote;
        ImageView imageViewEdit, imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            // Gan View vao ViewHolder
            viewHolder.textViewNote = convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lay gia tri
        final NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());
        //bat su kien nut cap nhat
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cap nhat"+notes.getNameNote(), Toast.LENGTH_SHORT).show();
                context.DialogCapNhatNotes(notes.getNameNote(), notes.getIdNote());
            }
        });
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDelete(notes.getNameNote(), notes.getIdNote());
            }
        });
        return convertView;
    }
}
