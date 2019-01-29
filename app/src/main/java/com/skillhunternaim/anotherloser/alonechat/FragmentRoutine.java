package com.skillhunternaim.anotherloser.alonechat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentRoutine extends Fragment {
    private ArrayList<ModelRoutinClassListItem> arr;
    private ArrayList<String> arrTime;
    AdapterRoutinClassList adapterCourse;
    AdapterRoutinTimeList adapterTime;
    ListView classList,timeList;
    ProgressBar progressBar;
    HorizontalScrollView horizontalScrollView;
    SharedPreferences userInfo;
    String university,dept,batch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_routine,null);
        horizontalScrollView=view.findViewById(R.id.horizontal_scroll_routine);
        classList=view.findViewById(R.id.id_routin_list_course);
        timeList=view.findViewById(R.id.id_routin_list_time);
        progressBar=view.findViewById(R.id.progressBar_routine);
        progressBar.setVisibility(View.VISIBLE);
        classList.setVisibility(View.GONE);
        timeList.setVisibility(View.GONE);

//Getting user info
        userInfo= Objects.requireNonNull(this.getActivity()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        university=userInfo.getString("university","");
        dept=userInfo.getString("dept","");
        batch=userInfo.getString("batch","");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Fetching Routine and Showing
        arr=new ArrayList<>();
        arrTime= new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().
                getReference(university+dept).child("Routins").child(batch);
        progressBar.setVisibility(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                arrTime.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    ModelRoutinClassListItem item=data.getValue(ModelRoutinClassListItem.class);
                    arr.add(item);
                    arrTime.add(Objects.requireNonNull(item).getTime());
                }
                adapterCourse=new AdapterRoutinClassList(getContext(),arr);
                adapterTime= new AdapterRoutinTimeList(Objects.requireNonNull(getContext()),arrTime);
                classList.setAdapter(adapterCourse);
                timeList.setAdapter(adapterTime);
                classList.setVisibility(View.VISIBLE);
                timeList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error in getting routine",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
