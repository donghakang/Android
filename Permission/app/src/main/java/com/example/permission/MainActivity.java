package com.example.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick = findViewById(R.id.btn_click);

        btnClick.setOnClickListener(this);
        chkPermission();
    }

    @Override
    public void onClick(View v) {
        saveFile();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("PERMISSION", "PERMISSION");

    }

    private void chkPermission() {
        int chk = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (chk == PackageManager.PERMISSION_DENIED) {
            // 익스터널 스토리지에 저장을 받았는가 안받았는가 물어봄
            String[] arr = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            // GPS
            // { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
            ActivityCompat.requestPermissions(this, arr, 3000);
        }
    }


    private void saveFile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download";
        File temp = new File(path);
        if (!temp.exists()) {
            temp.mkdirs();
        }
        String fileName = "hi.txt";
        String data = "안녕하세요";
        temp = new File(path + "/" + fileName);

        try {
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download";
        File temp = new File(path);
        if (!temp.exists()) {
            temp.mkdirs();
        }
        String fileName = "hi.txt";
        String data = "안녕하세요";
        temp = new File(path + "/" + fileName);

        try {
            FileInputStream fis = new FileInputStream(temp);
            BufferedReader br = new BufferedReader((new InputStreamReader(fis)));
            while (true) {
                String str = br.readLine();
                if (str != null) {
                    Log.d("HEU", str);
                } else {
                    break;
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}