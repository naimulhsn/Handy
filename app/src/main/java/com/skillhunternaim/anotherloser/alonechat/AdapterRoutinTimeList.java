package com.skillhunternaim.anotherloser.alonechat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class AdapterRoutinTimeList extends ArrayAdapter{
    private Context ctx;
    private ArrayList<String> arr;

    public AdapterRoutinTimeList(@NonNull Context context, ArrayList<String> arr) {
        super(context,R.layout.list_item_routine_time, arr);
        this.ctx=context;
        this.arr=arr;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(ctx);
        @SuppressLint("ViewHolder") View view=layoutInflater.inflate(R.layout.list_item_routine_time,parent,false);

        Button time=view.findViewById(R.id.routin_btn_class_time);
        time.setText(arr.get(position).trim());
        return view;
    }

}
