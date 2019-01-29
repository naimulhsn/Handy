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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText email_e,pass_e;
    TextInputLayout email_lay,pass_lay;
    ProgressBar progressBar;
    // Firebase  Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Firebase auth Initialize
        mAuth = FirebaseAuth.getInstance();
        // Firebase auth Initialize done


        progressBar=findViewById(R.id.progressBar_login);


        CheckInternetConnection connection=new CheckInternetConnection(this);
        connection.check();

        email_e=findViewById(R.id.id_login_email);
        pass_e=findViewById(R.id.id_login_pass);

        email_lay=findViewById(R.id.id_login_email_layout);
        pass_lay=findViewById(R.id.id_login_pass_layout);
        ////////////////////// cleare set error /////////////////////
        email_e.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_lay.setErrorEnabled(false);
                pass_lay.setErrorEnabled(false);
            }@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { } @Override public void afterTextChanged(Editable s) { }
        });
        pass_e.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_lay.setErrorEnabled(false);
                pass_lay.setErrorEnabled(false);
            }@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }@Override public void afterTextChanged(Editable s) { }
        });


    }
    public void signinClicked(View view){
        String email=email_e.getText().toString().trim();
        String pass=pass_e.getText().toString().trim();

        signIn(email,pass);
        //Toast.makeText(getApplicationContext(),"Signin Clicked\n"+"Email : "+email+"\nPassword : "+pass,Toast.LENGTH_SHORT).show();
    }
    public void signup(View view){
        Intent i=new Intent(getApplicationContext(),SignUp.class);
        startActivity(i);
        finish();
    }


    // [START sign_in_with_email]
    private void signIn(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            pass_lay.setError("Email or Password is incorrect!!!");
                            email_lay.setError("Email or Password is incorrect!!!");
                        }
                        progressBar.setVisibility(View.GONE);
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            //Toast.makeText(this,"Login First",Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(this,"Already Logged in",Toast.LENGTH_LONG).show();
            updateUI(currentUser);
        }

    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent i=new Intent(getApplicationContext(),Home.class);
            startActivity(i);
            finish();

        }
    }

}
