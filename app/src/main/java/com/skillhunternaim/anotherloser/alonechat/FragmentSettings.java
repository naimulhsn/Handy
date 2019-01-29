package com.skillhunternaim.anotherloser.alonechat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FragmentSettings extends Fragment {
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn=view.findViewById(R.id.id_fragment_setting_delete_account_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you Sure you want to delete your account???")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hey();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog= builder.create();
                alertDialog.show();


            }
        });
    }
    public void hey(){

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences s= Objects.requireNonNull(getActivity()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId=s.getString("userId","");
        String uni=s.getString("university","");
        String dept=s.getString("dept","");
        String batch=s.getString("batch","");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user").child(userId);
        reference.removeValue();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(""+uni+dept).child(batch).child(userId);
        ref.removeValue();
        if (user != null) {
            user.delete();
        }

        SharedPreferences.Editor editor=s.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(getActivity(),"Account Deleted!!!",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(),Login.class);
        getActivity().startActivity(intent);
    }
}
