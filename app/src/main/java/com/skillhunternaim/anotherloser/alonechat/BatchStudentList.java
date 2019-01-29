package com.skillhunternaim.anotherloser.alonechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BatchStudentList extends AppCompatActivity {
    AdapterBatchStudentList adapter;
    ArrayList<ModelBatchStudent> arr;
    ListView listView;

    private String uni,dpt,target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_student_list);
        listView=findViewById(R.id.batch_student_listview);
        listView.setVisibility(View.GONE);

        arr=new ArrayList<>();

        target =getIntent().getExtras().getString("targetBatch");
        SharedPreferences userInfo=getSharedPreferences("userInfo",MODE_PRIVATE);
        uni=userInfo.getString("university","");
        dpt=userInfo.getString("dept","");
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child(""+uni+dpt).child(target);
        Query query=ref.orderByChild("fullname");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){

                    ModelBatchStudent m=data.getValue(ModelBatchStudent.class);

                    arr.add(m);
                }

                adapter=new AdapterBatchStudentList(getApplicationContext(),arr);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
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

                Intent intent=new Intent(getApplicationContext(),ProfileOthers.class);
                intent.putExtra("userId",userId);
                intent.putExtra("university",uni);
                intent.putExtra("dept",dpt);
                intent.putExtra("batch",target);
                startActivity(intent);
            }
        });

    }
}
