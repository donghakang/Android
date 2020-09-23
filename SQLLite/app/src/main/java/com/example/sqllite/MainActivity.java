package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        SQLiteDatabase db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS member("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "age  INTEGER, "
                + "addr TEXT" + ");");
        // AUTOINCREMET 1 부터 자동으로 증가


        /** 삽입 **/
        db.execSQL("    INSERT INTO member (name, age, addr) VALUES ('하하', 10, 'Seoul')    ");

        String data1 = "INSERT INTO member (name, age, addr) VALUES ('동하', 26, 'Seoul')";
        String data2 = "INSERT INTO member (name, age, addr) VALUES ('수연', 28, 'Seoul')";
        String data3 = "INSERT INTO member (name, age, addr) VALUES ('푸른', 24, 'Seoul')";
        db.execSQL(data1);
        db.execSQL(data2);
        db.execSQL(data3);


        /** 검색 **/
        Cursor c = db.rawQuery("SELECT * FROM member", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Log.d("SQLTEST", "name: " + c.getString(1) + "   age: " + c.getInt(2) + "   addr: " + c.getString(3));
            c.moveToNext();
        }
        c.close();


        /** 삭제 **/
        String sql = "DELETE FROM member";          // 모든 데이터를 다 삭제한다.
        String sql_1 = sql + " WHERE idx=6";        // idx 6을 삭제한다.
        db.execSQL(sql);

        /** 수정 **/
        String fix_sql = "UPDATE member SET name='동하' WHERE idx=2");
        db.execSQL(fix_sql);

        db.close();
    }
}