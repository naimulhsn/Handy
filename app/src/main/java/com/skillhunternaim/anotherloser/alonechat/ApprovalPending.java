package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ApprovalPending extends AppCompatActivity {

    private AdapterBatchStudentList adapter;
    private ArrayList<ModelBatchStudent> arr;
    private ListView listView;
    private ProgressBar progressBar;

    private String uni,dpt,btch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_pending);

        listView=findViewById(R.id.pending_students_listview);
        listView.setVisibility(View.GONE);
        progressBar=findViewById(R.id.progressBar_pending_student_list);
        progressBar.setVisibility(View.VISIBLE);


        SharedPreferences userInfo=getSharedPreferences("userInfo",MODE_PRIVATE);
        uni=userInfo.getString("university","");
        dpt=userInfo.getString("dept","");
        btch=userInfo.getString("batch","");
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child(""+uni+dpt).child(btch);
        Query query=ref.orderByChild("fullname");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){

                    ModelBatchStudent m=data.getValue(ModelBatchStudent.class);

                    if(!Objects.requireNonNull(m).getApproved().equals("True")) {
                        arr.add(m);
                    }
                }

                adapter=new AdapterBatchStudentList(getApplicationContext(),arr);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Problem fetching batch students list",Toast.LENGTH_LONG).show();
                listView.setVisibility(View.VISIBLE);
            }
        });



        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);

        /// Response to select

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView user_id=view.findViewById(R.id.batch_students_list_userId);
                String userId=user_id.getText().toString();

                Intent intent=new Intent(getApplicationContext(),ProfileApproval.class);
                intent.putExtra("userId",userId);
                intent.putExtra("university",uni);
                intent.putExtra("dept",dpt);
                intent.putExtra("batch",btch);
                startActivity(intent);
            }
        });



    }
}
