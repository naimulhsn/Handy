package com.skillhunternaim.anotherloser.alonechat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowNote extends AppCompatActivity {
    SQLiteDatabaseOpenHelperForNotes sqLiteDataBaseOpenHelperForNotes;
    TextView title, text,date;
    String title_text,note_text,date_text;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        title=findViewById(R.id.id_show_note_title);
        text=findViewById(R.id.id_show_note_text);
        date=findViewById(R.id.id_show_note_date);



        sqLiteDataBaseOpenHelperForNotes=new SQLiteDatabaseOpenHelperForNotes(this);

        title_text=getIntent().getExtras().getString("title").trim();
        date_text=getIntent().getExtras().getString("date").trim();


        note_text=sqLiteDataBaseOpenHelperForNotes.getNoteText(date_text);

        title.setText(title_text);
        text.setText(note_text);
        date.setText(date_text);
    }

}
