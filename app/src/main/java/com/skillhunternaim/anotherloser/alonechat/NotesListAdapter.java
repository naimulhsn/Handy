package com.skillhunternaim.anotherloser.alonechat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesListAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> notesList;
    public NotesListAdapter(Context context, ArrayList<HashMap<String, String>> notes) {
        super(context,R.layout.custome_list_note,notes);
        this.mContext=context;
        notesList=notes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        @SuppressLint("ViewHolder") View view=layoutInflater.inflate(R.layout.custome_list_note,parent,false);
        TextView title=view.findViewById(R.id.id_custom_list_title);
        TextView date=view.findViewById(R.id.id_custom_list_creation_date);
        title.setText(notesList.get(position).get("title").trim());
        date.setText(notesList.get(position).get("date").trim());

        return view;
    }
}
