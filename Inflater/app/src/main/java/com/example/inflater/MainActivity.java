package com.example.inflater;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = findViewById(R.id.btn_click);

        btnClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDial();
    }


    private void showDial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title...");
        // builder.setMessage
//        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        })


        LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = lnf.inflate(R.layout.item_layout, null, false);
        TextView a = v.findViewById(R.id.tv_lnf);
        a.setText("LAYOUT INFLATER");

        builder.setView(v);

        builder.create().show();


    }
}