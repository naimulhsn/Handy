package com.skillhunternaim.anotherloser.alonechat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBatchStudentList extends ArrayAdapter {
    private Context ctx;
    private ArrayList<ModelBatchStudent> arr;
    private String fullname,academic_id, userId, photoUrl;
    private CircleImageView profilePic;

    public AdapterBatchStudentList(@NonNull Context context, ArrayList<ModelBatchStudent> a) {
        super(context, R.layout.list_item_batch_students,a);
        this.ctx=context;
        this.arr=a;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view==null){
            LayoutInflater layoutInflater= LayoutInflater.from(ctx);
            view=layoutInflater.inflate(R.layout.list_item_batch_students,parent,false);
        }


        TextView name= view.findViewById(R.id.batch_students_list_name);
        TextView id=view.findViewById(R.id.batch_students_list_academic_id);
        TextView user_id=view.findViewById(R.id.batch_students_list_userId);
        profilePic=view.findViewById(R.id.batch_students_list_image);

        fullname=arr.get(position).getFullname();
        academic_id=arr.get(position).getAcademic_id();
        userId=arr.get(position).getUserId();
        photoUrl=arr.get(position).getProfilePic();


        name.setText(fullname);
        id.setText(academic_id);
        user_id.setText(userId);

        if(photoUrl.equals("default")){
            profilePic.setImageResource(R.drawable.ic_profile_person_black_24dp);
        }else {
            Picasso.get().load(photoUrl)
                    .placeholder(R.drawable.ic_profile_person_black_24dp).error(R.drawable.ic_handy_app_icon)
                    .into(profilePic);
        }


        return view;
    }
}
