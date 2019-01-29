package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileOthers extends AppCompatActivity{
    SharedPreferences userInfo;
    TextView academic_id,fullname,gender,phone1,phone2,email,blood,current_address,hometown,university,dept,batch;
    CircleImageView image_profile;
    ImageView call_phone1,call_phone2,email_send;
    String userId,uni,dpt,btch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_others);

        image_profile=findViewById(R.id.profile_image_others);
        fullname=findViewById(R.id.profile_name_others);
        gender=findViewById(R.id.profile_gender_other);
        phone1=findViewById(R.id.profile_phone1_other);
        phone2=findViewById(R.id.profile_phone2_other);
        email=findViewById(R.id.profile_email_other);
        blood=findViewById(R.id.profile_blood_other);
        university=findViewById(R.id.profile_university_other);
        dept=findViewById(R.id.profile_dept_other);
        batch=findViewById(R.id.profile_batch_other);
        academic_id=findViewById(R.id.profile_academic_id_other);
        current_address=findViewById(R.id.profile_current_address_other);
        hometown=findViewById(R.id.profile_hometown_other);

        call_phone1=findViewById(R.id.profile_phone1_call_image_other);
        call_phone2=findViewById(R.id.profile_phone2_call_image_other);
        email_send=findViewById(R.id.profile_email_send_image_other);

        hide();


        userId=getIntent().getExtras().getString("userId","");
        uni=getIntent().getExtras().getString("university","");
        dpt=getIntent().getExtras().getString("dept","");
        btch=getIntent().getExtras().getString("batch","");


        setListeners();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("" + uni + dpt).child(btch).child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelBatchStudent m=dataSnapshot.getValue(ModelBatchStudent.class);

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

                if(m.getPhone1().isEmpty()){
                    call_phone1.setVisibility(View.GONE);
                }else call_phone1.setVisibility(View.VISIBLE);

                if(m.getPhone2().isEmpty()){
                    call_phone2.setVisibility(View.GONE);
                }else call_phone2.setVisibility(View.VISIBLE);
                if(m.getEmail().isEmpty()){
                    email_send.setVisibility(View.GONE);
                }else email_send.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error in Network connection!!!",Toast.LENGTH_SHORT).show();
                show();
            }
        });


//            String  aacademic_id, afullname, agender, aphone1, aphone2, aemail, ablood, acurrent_address,
//                    ahometown, auniversity, adept, abatch ;




//                    academic_id.setText(aacademic_id);
//                    fullname.setText(afullname);
//                    university.setText(auniversity);
//                    dept.setText(adept);
//                    batch.setText(abatch);
//                    gender.setText(agender);
//                    phone1.setText(aphone1);
//                    phone2.setText(aphone2);
//                    email.setText(aemail);
//                    current_address.setText(acurrent_address);
//                    hometown.setText(ahometown);
//                    blood.setText(ablood);
//                    if(aphone1.isEmpty()){
//                        call_phone1.setVisibility(View.GONE);
//                    }else call_phone1.setVisibility(View.VISIBLE);
//
//                    if(aphone2.isEmpty()){
//                        call_phone2.setVisibility(View.GONE);
//                    }else call_phone2.setVisibility(View.VISIBLE);
//                    if(aemail.isEmpty()){
//                        email_send.setVisibility(View.GONE);
//                    }else email_send.setVisibility(View.VISIBLE);


    }

    private void setListeners() {
        call_phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                String num=phone1.getText().toString().trim();
                i.setData(Uri.parse("tel:"+num));
                startActivity(i);
            }
        });
        call_phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                String num=phone2.getText().toString().trim();
                i.setData(Uri.parse("tel:"+num));
                startActivity(i);
            }
        });

        email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String mail=email.getText().toString().trim();
                i.putExtra(Intent.EXTRA_EMAIL,mail);
                i.putExtra(Intent.EXTRA_SUBJECT,"Sent from Handy");
                i.putExtra(Intent.EXTRA_TEXT,"Massage Body Here");
                i.setType("message/rfc822");
                Intent chooser =Intent.createChooser(i,"Launch Email");
                startActivity(chooser);
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

        call_phone1.setVisibility(View.GONE);
        call_phone2.setVisibility(View.GONE);
        email_send.setVisibility(View.GONE);
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

        call_phone1.setVisibility(View.VISIBLE);
        call_phone2.setVisibility(View.VISIBLE);
        email_send.setVisibility(View.VISIBLE);
    }
}
