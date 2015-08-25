package com.example.mikael.staffapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mig on 06-Aug-15.
 */
public class ChooseModeActivity extends Activity {

    private TextView connectStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);
        connectStatus = (TextView)findViewById(R.id.connect_status);

        if(!isConnected()){
            connectStatus.setVisibility(View.VISIBLE);
            connectStatus.setText("You are NOT conncted");
            AlertDialog.Builder builder = new AlertDialog.Builder(ChooseModeActivity.this);
            builder.setMessage("Please Connect Internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.show();
        }

        Button RoomStaff = (Button)findViewById(R.id.RoomStaffButton);
        RoomStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.example.mikael.staffapp.MainActivity");
                startActivity(i);
            }
        });

        Button PollStaff = (Button)findViewById(R.id.PollButton);
        PollStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.example.mikael.staffapp.PollActivity");
                i.putExtra("id"," ");
                startActivity(i);
            }
        });

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
