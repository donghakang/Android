package com.real.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    int curPage;
    public static final int FRAGMENT_A = 0;
    public static final int FRAGMENT_B = 1;

    FragmentA fragA = new FragmentA();
    FragmentB fragB = new FragmentB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body_rl, fragA);
        ft.commit();

        fragA.aaa();

    }

    public void changeScr(int scr){
        curPage =scr;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (scr){
            case FRAGMENT_A:
                ft.replace(R.id.body_rl, fragA);
                break;
            case FRAGMENT_B:
                ft.replace(R.id.body_rl, fragB);
                break;
        }
        ft.commit();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if(curPage == FRAGMENT_B){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.body_rl, fragB);
            ft.commit();
        }else if(curPage == FRAGMENT_A){
            finish();
        }

    }


}