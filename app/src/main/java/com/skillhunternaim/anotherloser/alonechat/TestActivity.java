package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TestActivity extends AppCompatActivity {
    private TextView t1,t2;
    private ProgressBar p;


    SharedPreferences userInfoPref;
    FirebaseUser currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        t1=findViewById(R.id.textView1);
        t2=findViewById(R.id.textView2);
        p=findViewById(R.id.progressBar_test_activity);
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);


        saveUserInfoInSharedPref();

    }
    private void saveUserInfoInSharedPref() {
        currUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("user");
        ref.child(Objects.requireNonNull(currUser.getDisplayName())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelStudent m=dataSnapshot.getValue(ModelStudent.class);

                userInfoPref=getSharedPreferences("userInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = userInfoPref.edit();
                editor.putString("fullname", Objects.requireNonNull(m).getFullname());
                editor.putString("university",m.getUniversity());
                editor.putString("dept",m.getDept());
                editor.putString("batch",m.getBatch());
                editor.putString("user_id",currUser.getDisplayName());
                editor.apply();
                DatabaseReference mRef=FirebaseDatabase.getInstance().getReference().
                        child(m.getUniversity()+m.getDept()).child(m.getBatch()).child(currUser.getDisplayName());
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ModelBatchStudent m=dataSnapshot.getValue(ModelBatchStudent.class);
                        if(Objects.requireNonNull(m).getApproved().equals("True")){
                            Intent intent=new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                            finish();
                        }else {
                            p.setVisibility(View.GONE);
                            t1.setVisibility(View.VISIBLE);
                            t2.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error in getting dir",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
