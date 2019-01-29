package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    TextView splashText;
    ImageView imageView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashText=findViewById(R.id.id_splash_text);
        imageView=findViewById(R.id.id_splash_image);
        Animation FadeIn= AnimationUtils.loadAnimation(this,R.anim.fade_in_accelerate);
        splashText.setAnimation(FadeIn);
        imageView.setAnimation(FadeIn);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    mAuth=FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        Intent i=new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                        finish();
                    }else {
                        Intent i=new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
        thread.start();
    }
}
