package com.skillhunternaim.anotherloser.alonechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {
    private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        btn1=findViewById(R.id.admin_pending);
        btn2=findViewById(R.id.admin_update_routine);
    }
}
