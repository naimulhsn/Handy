package com.skillhunternaim.anotherloser.alonechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ProfileApproval extends AppCompatActivity {
    TextView academic_id,fullname,gender,phone1,phone2,email,blood,current_address,hometown,university,dept,batch;
    ImageView call_phone1,call_phone2,email_send;
    Button btn_approve,btn_delete;
    String userId,uni,dpt,btch,uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_approval);

        fullname=findViewById(R.id.profile_name_others_approval);
        gender=findViewById(R.id.profile_gender_other_approval);
        phone1=findViewById(R.id.profile_phone1_other_approval);
        phone2=findViewById(R.id.profile_phone2_other_approval);
        email=findViewById(R.id.profile_email_other_approval);
        blood=findViewById(R.id.profile_blood_other_approval);
        university=findViewById(R.id.profile_university_other_approval);
        dept=findViewById(R.id.profile_dept_other_approval);
        batch=findViewById(R.id.profile_batch_other_approval);
        academic_id=findViewById(R.id.profile_academic_id_other_approval);
        current_address=findViewById(R.id.profile_current_address_other_approval);
        hometown=findViewById(R.id.profile_hometown_other_approval);

        call_phone1=findViewById(R.id.profile_phone1_call_image_other_approval);
        call_phone2=findViewById(R.id.profile_phone2_call_image_other_approval);
        email_send=findViewById(R.id.profile_email_send_image_other_approval);

        btn_approve=findViewById(R.id.approval_btn_approve);
        btn_delete=findViewById(R.id.approval_btn_delete);

        hide();
        setListeners();

        userId=getIntent().getExtras().getString("userId","");
        uni=getIntent().getExtras().getString("university","");
        dpt=getIntent().getExtras().getString("dept","");
        btch=getIntent().getExtras().getString("batch","");



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("" + uni + dpt).child(btch).child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelBatchStudent m=dataSnapshot.getValue(ModelBatchStudent.class);

                fullname.setText(Objects.requireNonNull(m).getFullname());
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
                uId=m.getuId();

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
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve();

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });



    }


    public void approve(){
        final ProgressDialog pd = new ProgressDialog(ProfileApproval.this);
        pd.setTitle("Approving....");
        pd.setMessage("please wait.");
        btn_approve.setClickable(false);
        btn_delete.setClickable(false);
        pd.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(""+uni+dpt).child(btch).child(userId);

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("approved","true");
        reference.updateChildren(hashMap);
        pd.dismiss();
        finish();

    }

    public void delete() {
        final ProgressDialog pd = new ProgressDialog(ProfileApproval.this);
        pd.setTitle("Deleting....");
        pd.setMessage("please wait.");
        btn_approve.setClickable(false);
        btn_delete.setClickable(false);
        pd.show();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user").child(userId);

        reference.removeValue();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(""+uni+dpt).child(btch).child(userId);
        ref.removeValue();

        pd.dismiss();
        finish();

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

        btn_approve.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
    }

    private void show() {
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

        btn_approve.setVisibility(View.VISIBLE);
        btn_delete.setVisibility(View.VISIBLE);
    }
}
