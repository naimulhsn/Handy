package com.skillhunternaim.anotherloser.alonechat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentsListNotes extends Fragment {
    SQLiteDatabaseOpenHelperForNotes sqLiteDatabaseOpenHelperForNotes;
    ListView listView;
    String date_textt;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=view.findViewById(R.id.id_lists);
        sqLiteDatabaseOpenHelperForNotes=new SQLiteDatabaseOpenHelperForNotes(getContext());
        ArrayList<HashMap<String, String>> notesList=sqLiteDatabaseOpenHelperForNotes.getAllNoteList();
        NotesListAdapter adapter=new NotesListAdapter(getContext(),notesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title=view.findViewById(R.id.id_custom_list_title);
                TextView date=view.findViewById(R.id.id_custom_list_creation_date);

                String title_text=title.getText().toString();
                date_textt=date.getText().toString();
                Intent intent=new Intent(getContext(),ShowNote.class);
                intent.putExtra("title",title_text);
                intent.putExtra("date",date_textt);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView date=view.findViewById(R.id.id_custom_list_creation_date);
                date_textt=date.getText().toString().trim();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete this Note")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int res=sqLiteDatabaseOpenHelperForNotes.deleteNote(date_textt);
                                if(res>0) Toast.makeText(getContext().getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                else Toast.makeText(getContext().getApplicationContext(),"Error!!!",Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                Fragment fragment=new FragmentsListNotes();
                                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.id_content_fragment_holder,fragment);
                                fragmentTransaction.commit();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog= builder.create();
                alertDialog.setTitle("Wait!!!");
                alertDialog.show();
                return true;
            }
        });

        fab=view.findViewById(R.id.id_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Add a new Note",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getContext(),NewNote.class);
                startActivity(intent);
            }
        });
    }
}
