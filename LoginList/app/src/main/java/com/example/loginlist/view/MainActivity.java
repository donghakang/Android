package com.example.loginlist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.loginlist.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvRegister, tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegister = findViewById(R.id.tv_register);
        tvLogin    = findViewById(R.id.tv_login);

        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_login:
                intent = new Intent(this, com.example.loginlist.view.LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                intent = new Intent(this, com.example.loginlist.view.RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}