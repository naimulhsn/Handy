package com.skillhunternaim.anotherloser.alonechat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class AdapterRoutinClassList extends ArrayAdapter {

    private Context mContext;
    private ArrayList<ModelRoutinClassListItem> models;
    public AdapterRoutinClassList(Context context, ArrayList<ModelRoutinClassListItem> model) {
        super(context,R.layout.list_item_routin_class,model);
        this.mContext=context;
        this.models=model;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        @SuppressLint("ViewHolder") View view=layoutInflater.inflate(R.layout.list_item_routin_class,parent,false);

        String text=models.get(position).getSun();

        Button sun=view.findViewById(R.id.routin_btn_class_sun);
        sun.setText(text);
        if(text.equals(""))sun.setBackgroundColor(Color.TRANSPARENT);


        Button mon=view.findViewById(R.id.routin_btn_class_mon);
        text=models.get(position).getMon();
        mon.setText(text);
        if(text.equals(""))mon.setBackgroundColor(Color.TRANSPARENT);

        final Button tue=view.findViewById(R.id.routin_btn_class_tue);
        text=models.get(position).getTue();
        tue.setText(text);
        if(text.equals(""))tue.setBackgroundColor(Color.TRANSPARENT);

        Button wed=view.findViewById(R.id.routin_btn_class_wed);
        text=models.get(position).getWed();
        wed.setText(text);
        if(text.equals(""))wed.setBackgroundColor(Color.TRANSPARENT);

        Button thu=view.findViewById(R.id.routin_btn_class_thu);
        text=models.get(position).getThu();
        thu.setText(text);
        if(text.equals(""))thu.setBackgroundColor(Color.TRANSPARENT);



        return view;
    }
}