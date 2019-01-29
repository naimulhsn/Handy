package com.skillhunternaim.anotherloser.alonechat;

import android.content.Context;
//import android.app.Dialog;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.Objects;

public class CheckInternetConnection {
    private Context contxt;

    public CheckInternetConnection(Context context) {
        this.contxt = context;

    }

    public void check(){
        ConnectivityManager cm =
                (ConnectivityManager)contxt.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        boolean iss = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!iss){
            Toast.makeText(contxt,"Check your Internet connection!!!",Toast.LENGTH_LONG).show();
//            Dialog dialog=new Dialog(contxt);
//            dialog.setContentView(R.layout.no_interenet_popup);
//            TextView textView=dialog.findViewById(R.id.no_internet_text);
//            textView.setText("Check your internet !");
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.show();

        }
    }
}
