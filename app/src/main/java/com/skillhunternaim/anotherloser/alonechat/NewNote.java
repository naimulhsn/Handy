package com.skillhunternaim.anotherloser.alonechat;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNote extends AppCompatActivity {
    TextInputEditText textInputEditTextTitle,textInputEditTextText;
    Button save_btn;
    SQLiteDatabaseOpenHelperForNotes sqLiteDatabaseOpenHelperForNotes;

    String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        textInputEditTextTitle=findViewById(R.id.id_new_note_title);
        textInputEditTextText=findViewById(R.id.id_new_note_text);
        save_btn=findViewById(R.id.id_new_note_save_btn);


        sqLiteDatabaseOpenHelperForNotes= new SQLiteDatabaseOpenHelperForNotes(this);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=textInputEditTextTitle.getText().toString().trim();
                String text=textInputEditTextText.getText().toString().trim();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.ENGLISH);
                String date = df.format(currentTime).trim();
                Boolean res=sqLiteDatabaseOpenHelperForNotes.addNote(title,text,date,password);
                if(res){
                    //onBackPressed();
                    Toast.makeText(getApplicationContext(),"Successfully added",Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(getApplicationContext(),Notes.class);
//                    startActivity(intent);
//                    finish();
                    Fragment fragment=new FragmentsListNotes();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.id_content_fragment_holder,fragment);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getApplicationContext(),"Some error Encountered!!!",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
