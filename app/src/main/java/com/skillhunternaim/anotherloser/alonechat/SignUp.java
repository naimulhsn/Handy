package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.sleep;

public class SignUp extends AppCompatActivity {
    Spinner spinner_university, spinner_dept, spinner_batch, spinner_blood;
    TextInputEditText e_fullname, e_academic_id, e_phone1, e_phone2, e_email, e_current_address, e_hometown, e_pass, e_pass_confirm;
    TextInputLayout e_fullname_lay, e_academic_id_lay, e_phone1_lay, e_phone2_lay, e_email_lay, e_current_address_lay, e_hometown_lay, e_pass_lay, e_pass_confirm_lay;
    TextView radioBtnError, universityError, deptError, bloodError, batchError, formInComplete;
    RadioGroup radioGender;
    ProgressBar progressBar;
    /////////////////////predefined ////////////////
    String[] bloodgrp = {"Blood Group", "B+", "B-", "A+", "A-", "AB-", "AB+", "O+", "O-"};
    String[] universities = {"Select University", "BSMRSTU"};
    String[] departments = {"Select Dept.", "CSE"};
    String[] batchs = {"Select Batch","8", "7", "6", "5"};


    String academic_id, fullname, gender = "", phone1, phone2, email, blood = "", current_address, hometown, university = "", dept = "", batch = "", pass, pass_confirm;

    SharedPreferences userInfoPref;
    // Firebase  Auth
    private FirebaseAuth mAuth;
    String userid;

    FirebaseDatabase database;
    DatabaseReference  myRef,userRef,routinRef;

    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        cnt=1;
        // Firebase auth Initialize
        mAuth = FirebaseAuth.getInstance();
        // Firebase auth Initialize done
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        progressBar = findViewById(R.id.progressBar_signup);

        /////////////////////////////////////       findView of TextInputEditext    //////////////////////////////////////////////////////
        e_fullname = findViewById(R.id.id_signup_fullname);
        e_academic_id = findViewById(R.id.id_signup_academic_id);
        e_phone1 = findViewById(R.id.id_signup_Phone1);
        e_phone2 = findViewById(R.id.id_signup_phone2);
        e_email = findViewById(R.id.id_signup_email);
        e_current_address = findViewById(R.id.id_signup_curr_address);
        e_hometown = findViewById(R.id.id_signup_hometown);
        e_pass = findViewById(R.id.id_signup_pass);
        e_pass_confirm = findViewById(R.id.id_signup_pass_confirm);

        /////////////////////////////////////       findView of TextInputLayout For Setting Error   //////////////////////////////////////////////////////
        e_fullname_lay = findViewById(R.id.id_signup_fullname_lay);
        e_academic_id_lay = findViewById(R.id.id_signup_academic_id_lay);
        e_phone1_lay = findViewById(R.id.id_signup_Phone1_lay);
        e_phone2_lay = findViewById(R.id.id_signup_phone2_lay);
        e_email_lay = findViewById(R.id.id_signup_email_lay);
        e_current_address_lay = findViewById(R.id.id_signup_curr_address_lay);
        e_hometown_lay = findViewById(R.id.id_signup_hometown_lay);
        e_pass_lay = findViewById(R.id.id_signup_pass_lay);
        e_pass_confirm_lay = findViewById(R.id.id_signup_pass_confirm_lay);

/////////////////////////////////////       findView of Error TextView    //////////////////////////////////////////////////////
        radioBtnError = findViewById(R.id.id_signup_radioButton_error);
        universityError = findViewById(R.id.id_signup_spinner_university_error);
        deptError = findViewById(R.id.id_signup_spinner_dept_error);
        batchError = findViewById(R.id.id_signup_spinner_batch_error);
        bloodError = findViewById(R.id.id_signup_spinner_bloodgroup_error);
        formInComplete = findViewById(R.id.id_signup_form_incomplete);

///////////////////////////////////////       RadioGroup     //////////////////////////////////////////////////////
        radioGender = findViewById(R.id.id_signup_radiogroup_gender);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioBtnError.setVisibility(View.GONE);
                switch (checkedId) {
                    case R.id.id_signup_radioButton_male:
                        gender = "Male";
                        break;

                    case R.id.id_signup_radioButton_female:
                        gender = "Female";
                        break;
                }
            }
        });

        /////////////////////////////////////       Spinner     //////////////////////////////////////////////////////
        spinner_blood = findViewById(R.id.id_signup_spinner_bloodgroup);
        spinner_university = findViewById(R.id.id_signup_spinner_university);
        spinner_dept = findViewById(R.id.id_signup_spinner_dept);
        spinner_batch = findViewById(R.id.id_signup_spinner_batch);
        ArrayAdapter<String> adapter_blood = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bloodgrp);
        ArrayAdapter<String> adapter_university = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, universities);
        ArrayAdapter<String> adapter_dept = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        ArrayAdapter<String> adapter_batch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, batchs);
        spinner_blood.setAdapter(adapter_blood);
        spinner_university.setAdapter(adapter_university);
        spinner_dept.setAdapter(adapter_dept);
        spinner_batch.setAdapter(adapter_batch);

        spinner_blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood = parent.getItemAtPosition(position).toString().trim();
                if (blood.equals("Blood Group")) blood = "";
                else {
                    bloodError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                blood = "";
            }
        });
        spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                university = parent.getItemAtPosition(position).toString().trim();
                if (university.equals("Select University")) university = "";
                else {
                    universityError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                university = "";
            }
        });
        spinner_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = parent.getItemAtPosition(position).toString().trim();
                if (dept.equals("Select Dept.")) dept = "";
                else {
                    deptError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dept = "";
            }
        });
        spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batch = parent.getItemAtPosition(position).toString().trim();
                if (batch.equals("Select Batch")) batch = "";
                else {
                    batchError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                batch = "";
            }
        });


/////////////////////////////////////  Clear Error massage //////////////////////////////////////////////////////
        e_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_fullname_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_academic_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_academic_id_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_phone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_phone1_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_phone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_phone2_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_email_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_current_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_current_address_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_hometown.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_hometown_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_pass_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        e_pass_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                e_pass_confirm_lay.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


/////////////////////////////////////END OF ON CREATE METHOD //////////////////////////////////////////////////////
    }

    public void signup(View view) {
        fullname = e_fullname.getText().toString().trim();
        academic_id = e_academic_id.getText().toString().trim().toUpperCase();
        phone1 = e_phone1.getText().toString().trim();
        phone2 = e_phone2.getText().toString().trim();
        email = e_email.getText().toString().trim().toLowerCase();
        current_address = e_current_address.getText().toString().trim();
        hometown = e_hometown.getText().toString().trim();
        pass = e_pass.getText().toString().trim();
        pass_confirm = e_pass_confirm.getText().toString().trim();
        boolean isEverythingOk = validateSignUpForm();


        ///////////////////////      Ready to Go          ///////////////////////
        if (isEverythingOk) {
            cnt=1;
           createAccount();

        }else {
            PlayFormIncompleteAnimation();
        }

    }

    private void createAccount() {
        progressBar.setVisibility(View.VISIBLE);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            cnt*=2;
                            // Sign in success, update UI with the signed-in user's information
                            saveToDatabase();
                        } else {
                            //Check if email already registered
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "This email is Already Registered", Toast.LENGTH_SHORT).show();
                                e_email_lay.setError("Email Already Registered");
                                PlayFormIncompleteAnimation();
                            }else {
                                Toast.makeText(getApplicationContext(),"Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            // If sign up fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private void saveToDatabase() {
        userid=generateUserId();
        setDisplayName();


    }

    private void setDisplayName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profileChangeRequest=
                    new UserProfileChangeRequest.Builder()
                    .setDisplayName(userid)
                    .build();
            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                saveToBatch();
                                //Toast.makeText(getApplicationContext(),"uid is now as displayName",Toast.LENGTH_SHORT).show();
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"uid as displayName failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void saveToBatch() {

        //set info to data model
        ModelBatchStudent modelBatchStudent=new ModelBatchStudent();
        modelBatchStudent.setProfilePic("default");
        modelBatchStudent.setFullname(fullname);
        modelBatchStudent.setUniversity(university);
        modelBatchStudent.setDept(dept);
        modelBatchStudent.setBatch(batch);
        modelBatchStudent.setAcademic_id(academic_id);
        modelBatchStudent.setGender(gender);
        modelBatchStudent.setPhone1(phone1);
        modelBatchStudent.setPhone2(phone2);
        modelBatchStudent.setEmail(email);
        modelBatchStudent.setBlood(blood);
        modelBatchStudent.setCurrent_address(current_address);
        modelBatchStudent.setHometown(hometown);
        modelBatchStudent.setUserId(userid);

        myRef.child(""+university+dept).child(batch).child(userid).setValue(modelBatchStudent).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            saveToUser();
                            //Toast.makeText(getApplicationContext(),"Successful to batch",Toast.LENGTH_SHORT).show();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Error to batch :"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void saveToUser() {

        // Model Student
        ModelStudent modelStudent=new ModelStudent();
        modelStudent.setFullname(fullname);
        modelStudent.setUniversity(university);
        modelStudent.setDept(dept);
        modelStudent.setBatch(batch);

        userRef=database.getReference("user");

        userRef.child(userid).setValue(modelStudent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            saveToSharedPref();
                            //Toast.makeText(getApplicationContext(),"Successful to user",Toast.LENGTH_SHORT).show();

                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Error to user :"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void saveToSharedPref() {
        userInfoPref=getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfoPref.edit();
        editor.putString("fullname", fullname);
        editor.putString("university",university);
        editor.putString("dept",dept);
        editor.putString("batch",batch);
        editor.putString("userId",userid);
        editor.apply();

        progressBar.setVisibility(View.GONE);
        Intent intent=new Intent(this,Home.class);
        startActivity(intent);
        finish();

    }


    ///////other functions///

    private String generateUserId() {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSUVTWXYZ0123456789";
        for(int i=0; i<10; i++){
            stringBuilder.append(alphabet.charAt(Math.abs(r.nextInt(alphabet.length()))));
        }
        return fullname+stringBuilder.toString();
    }

    private boolean validateSignUpForm() {
        boolean isEverythingOk = true;
        //name
        if (fullname.isEmpty()) {
            isEverythingOk = false;
            e_fullname_lay.setError("You must enter your Academic Name");
        } else {
            for (int i = 0; i < fullname.length(); i++) {
                if ((fullname.charAt(i) < 'a' || fullname.charAt(i) > 'z') && (fullname.charAt(i) < 'A' || fullname.charAt(i) > 'Z') && fullname.charAt(i) != ' ') {
                    isEverythingOk = false;
                    e_fullname_lay.setError("Your Academic Name Can not contain any special Characters");
                    break;
                }
            }
        }
        //university
        if (university.isEmpty()) {
            isEverythingOk = false;
            universityError.setVisibility(View.VISIBLE);
        }
        //dept
        if (dept.isEmpty()) {
            isEverythingOk = false;
            deptError.setVisibility(View.VISIBLE);
        }
        //batch
        if (batch.isEmpty()) {
            isEverythingOk = false;
            batchError.setVisibility(View.VISIBLE);
        }
        //blood
        if (blood.isEmpty()) {
            isEverythingOk = false;
            bloodError.setVisibility(View.VISIBLE);
        }
        //Academic id
        if (academic_id.isEmpty()) {
            isEverythingOk = false;
            e_academic_id_lay.setError("Must enter your academic ID");
        }
        //Gender
        if (gender.isEmpty()) {
            isEverythingOk = false;
            radioBtnError.setVisibility(View.VISIBLE);
        }
        //phone1
        if (phone1.isEmpty()) {
            isEverythingOk = false;
            e_phone1_lay.setError("Must enter a Phone Number");
        }
        //email
        if (email.isEmpty()) {
            isEverythingOk = false;
            e_email_lay.setError("You must enter your email address");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEverythingOk = false;
            e_email_lay.setError("Invalid email address");
        }
        //current address
        if (current_address.isEmpty()) {
            isEverythingOk = false;
            e_current_address_lay.setError("You must enter your Current address");
        }
        //hometown
        if (hometown.isEmpty()) {
            isEverythingOk = false;
            e_hometown_lay.setError("You must enter your Hometown");
        }

        //Check Password
        if (pass.isEmpty() || pass.length() < 6) {
            isEverythingOk = false;
            e_pass_lay.setError("Password must have minimum 6 characters");
            e_pass_confirm_lay.setError("Password must have minimum 6 characters");
        } else if (!pass.equals(pass_confirm)) {
            isEverythingOk = false;
            e_pass_confirm_lay.setError("Passwords doesn't match!!!");
        }
        return isEverythingOk;
    }

    private void PlayFormIncompleteAnimation() {
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        formInComplete.setVisibility(View.VISIBLE);
        formInComplete.setAnimation(fadeOut);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    formInComplete.post(new Runnable() {
                        public void run() {
                            formInComplete.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        thread.start();
    }
}

























//////////////////////////////////////////////////////////////////////
//        for(int j=5;j<=8;j++){
//            for(int k=1; k<=8; k++){
//                ModelRoutinClassListItem modelRoutinClassListItem=new ModelRoutinClassListItem();
//                modelRoutinClassListItem.setTime(k+" am");
//                modelRoutinClassListItem.setSun("CSE301");
//                modelRoutinClassListItem.setMon("CSE302");
//                modelRoutinClassListItem.setTue("CSE303");
//                modelRoutinClassListItem.setWed("CSE304");
//                modelRoutinClassListItem.setThu("CSE305");
//                modelRoutinClassListItem.setFri("");
//                modelRoutinClassListItem.setSat("");
//                routinRef=database.getReference();
//                routinRef.child(rootdir).child("Routins").child(""+j).child(""+k).setValue(modelRoutinClassListItem).
//                        addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//                                }
//
//                            }
//                        });
//            }
//        }