package com.skillhunternaim.anotherloser.alonechat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditOwnProfile extends AppCompatActivity {
    private static final int REQUEST_IMAGE = 111;
    SharedPreferences userAllInfo;
    EditText phone1,phone2,current_address,hometown;
    CircleImageView image_profile;
    Button btn;

    Uri imageUri;
    StorageReference storageReference;
    StorageTask uploadtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_own_profile);
        storageReference= FirebaseStorage.getInstance().getReference();

        image_profile=findViewById(R.id.profile_image_own_edit);
        phone1=findViewById(R.id.profile_phone1_own_edit);
        phone2=findViewById(R.id.profile_phone2_own_edit);
        current_address=findViewById(R.id.profile_current_address_own_edit);
        hometown=findViewById(R.id.profile_hometown_own_edit);
        btn = findViewById(R.id.profile_save_info_card_own_edit);

        userAllInfo=getSharedPreferences("userAllInfo",MODE_PRIVATE);
        phone1.setText(userAllInfo.getString("phone1",""));
        phone2.setText(userAllInfo.getString("phone2",""));
        current_address.setText(userAllInfo.getString("current_address",""));
        hometown.setText(userAllInfo.getString("hometown",""));
        String photoUrl=userAllInfo.getString("profilePic","");

        setPhoto(photoUrl);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Btn clicked",Toast.LENGTH_SHORT).show();
                updateInfo();
            }
        });
        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });

    }

    private void getPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode ==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();

            if(uploadtask != null ){

            }else{
                String photoUrl=imageUri.toString();
                setPhoto(photoUrl);
                // uploadFile();
                // Toast.makeText(getContext(), "unable to open file", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = EditOwnProfile.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void setPhoto(String photoUrl) {
        if(photoUrl.equals("default")){
            image_profile.setImageResource(R.drawable.ic_profile_person_black_24dp);
        }else {
            Picasso.get().load(photoUrl)
                    .placeholder(R.drawable.ic_profile_person_black_24dp).error(R.drawable.ic_handy_app_icon)
                    .into(image_profile);
        }
    }





    public void updateInfo(){
        final ProgressDialog pd = new ProgressDialog(EditOwnProfile.this);
        pd.setTitle("Updating info....");
        pd.setMessage("please wait.");
        btn.setClickable(false);
        pd.show();
        Uri uri=imageUri;
        if(uri!=null){
            final StorageReference fileReference = storageReference.child("images/"+System.currentTimeMillis()
                    +"."+getFileExtension(uri));
            uploadtask = fileReference.putFile(uri);
            uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = (Uri) task.getResult();
                        String mUri = downloadUri.toString();

                        userAllInfo=getSharedPreferences("userAllInfo",MODE_PRIVATE);
                        String uni=userAllInfo.getString("university","");
                        String dpt=userAllInfo.getString("dept","");
                        String btch=userAllInfo.getString("batch","");
                        String userId=userAllInfo.getString("userId","");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(""+uni+dpt).child(btch).child(userId);


                        HashMap<String , Object>hashMap = new HashMap<>();
                        hashMap.put("profilePic",mUri);
                        hashMap.put("phone1",phone1.getText().toString());
                        hashMap.put("phone2",phone2.getText().toString());
                        hashMap.put("current_address", current_address.getText().toString());
                        hashMap.put("hometown",hometown.getText().toString());

                        reference.updateChildren(hashMap);



                        pd.dismiss();
                        goToProfile();

                    }else {
                        Toast.makeText(EditOwnProfile.this, "failed to Update Info", Toast.LENGTH_SHORT).show();
                        btn.setClickable(true);
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else {
            userAllInfo=getSharedPreferences("userAllInfo",MODE_PRIVATE);
            String uni=userAllInfo.getString("university","");
            String dpt=userAllInfo.getString("dept","");
            String btch=userAllInfo.getString("batch","");
            String userId=userAllInfo.getString("userId","");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(""+uni+dpt).child(btch).child(userId);

            reference.setValue("phone1",phone1.getText().toString());
            reference.setValue("phone2",phone2.getText().toString());
            reference.setValue("current_address",current_address.getText().toString());
            reference.setValue("hometown",hometown.getText().toString());

            pd.dismiss();
            goToProfile();
        }

    }

    private void goToProfile() {
        Intent intent=new Intent(getApplicationContext(),ProfilleOwn.class);
        startActivity(intent);
        finish();
    }
}
