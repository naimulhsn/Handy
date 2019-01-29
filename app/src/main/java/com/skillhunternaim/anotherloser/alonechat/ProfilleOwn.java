package com.skillhunternaim.anotherloser.alonechat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilleOwn extends AppCompatActivity {
    FloatingActionButton fab;
    SharedPreferences userInfo,userAllInfo;
    TextView academic_id,fullname,gender,phone1,phone2,email,blood,current_address,hometown,university,dept,batch;
    CircleImageView image_profile;
    String uni,dpt,btch,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profille_own);

        fab=findViewById(R.id.profile_fab_edit_btn_own);
        image_profile=findViewById(R.id.profile_image_own);
        fullname=findViewById(R.id.profile_name_own);
        gender=findViewById(R.id.profile_gender_own);
        phone1=findViewById(R.id.profile_phone1_own);
        phone2=findViewById(R.id.profile_phone2_own);
        email=findViewById(R.id.profile_email_own);
        blood=findViewById(R.id.profile_blood_own);
        university=findViewById(R.id.profile_university_own);
        dept=findViewById(R.id.profile_dept_own);
        batch=findViewById(R.id.profile_batch_own);
        academic_id=findViewById(R.id.profile_academic_id_own);
        current_address=findViewById(R.id.profile_current_address_own);
        hometown=findViewById(R.id.profile_hometown_own);
        hide();



        userInfo= getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uni = userInfo.getString("university", "");
        dpt = userInfo.getString("dept", "");
        btch = userInfo.getString("batch", "");
        user_id = userInfo.getString("user_id", "");


        userAllInfo=getSharedPreferences("userAllInfo",MODE_PRIVATE);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("" + uni + dpt).child(btch).child(user_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelBatchStudent m=dataSnapshot.getValue(ModelBatchStudent.class);

                SharedPreferences.Editor editor=userAllInfo.edit();

                editor.putString("profilePic",m.getProfilePic());
                editor.putString("phone1",m.getPhone1());
                editor.putString("phone2",m.getPhone2());
                editor.putString("current_address",m.getCurrent_address());
                editor.putString("hometown",m.getHometown());
                editor.putString("university",uni);
                editor.putString("dept",dpt);
                editor.putString("batch",btch);
                editor.putString("userId",user_id);
                editor.apply();

                if(! m.getProfilePic().equals("default")){
                    Picasso.get().load(m.getProfilePic())
                            .placeholder(R.drawable.ic_profile_person_black_24dp).error(R.drawable.ic_handy_app_icon)
                            .into(image_profile);
                }
                else image_profile.setImageResource(R.drawable.ic_profile_person_black_24dp);

                fullname.setText(m.getFullname());
                blood.setText(m.getBlood());
                gender.setText(m.getGender());
                phone1.setText(m.getPhone1());
                phone2.setText(m.getPhone2());
                email.setText(m.getEmail());
                blood.setText(m.getBlood());
                university.setText(m.getUniversity());
                dept.setText(m.getDept());
                batch.setText(m.getBatch());
                academic_id.setText(m.getAcademic_id());
                current_address.setText(m.getCurrent_address());
                hometown.setText(m.getHometown());
                show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditOwnProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void hide() {
        image_profile.setVisibility(View.GONE);
        fullname.setVisibility(View.GONE);
        blood.setVisibility(View.GONE);
        gender.setVisibility(View.GONE);
        phone1.setVisibility(View.GONE);
        phone2.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        blood.setVisibility(View.GONE);
        university.setVisibility(View.GONE);
        dept.setVisibility(View.GONE);
        batch.setVisibility(View.GONE);
        academic_id.setVisibility(View.GONE);
        current_address.setVisibility(View.GONE);
        hometown.setVisibility(View.GONE);
        fab.setClickable(false);
    }

    private void show() {
        image_profile.setVisibility(View.VISIBLE);
        fullname.setVisibility(View.VISIBLE);
        blood.setVisibility(View.VISIBLE);
        gender.setVisibility(View.VISIBLE);
        phone1.setVisibility(View.VISIBLE);
        phone2.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        blood.setVisibility(View.VISIBLE);
        university.setVisibility(View.VISIBLE);
        dept.setVisibility(View.VISIBLE);
        batch.setVisibility(View.VISIBLE);
        academic_id.setVisibility(View.VISIBLE);
        current_address.setVisibility(View.VISIBLE);
        hometown.setVisibility(View.VISIBLE);
        fab.setClickable(true);
    }
}


