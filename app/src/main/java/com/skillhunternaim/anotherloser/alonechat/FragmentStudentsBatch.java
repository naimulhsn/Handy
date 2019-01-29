package com.skillhunternaim.anotherloser.alonechat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentStudentsBatch extends Fragment {
    Button btch8,btch7,btch6,btch5;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_students_batch,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btch5=view.findViewById(R.id.batch_list_5th);
        btch6=view.findViewById(R.id.batch_list_6th);
        btch7=view.findViewById(R.id.batch_list_7th);
        btch8=view.findViewById(R.id.batch_list_8th);


        btch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BatchStudentList.class);
                intent.putExtra("targetBatch","5");
                startActivity(intent);
            }
        });
        btch6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BatchStudentList.class);
                intent.putExtra("targetBatch","6");
                startActivity(intent);
            }
        });
        btch7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BatchStudentList.class);
                intent.putExtra("targetBatch","7");
                startActivity(intent);
            }
        });
        btch8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BatchStudentList.class);
                intent.putExtra("targetBatch","8");
                startActivity(intent);
            }
        });

    }

}
