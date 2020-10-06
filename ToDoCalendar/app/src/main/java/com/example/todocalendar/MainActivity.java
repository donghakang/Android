package com.example.todocalendar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    SQLiteDatabase db;

    GridView gv;
    GridView gridDay;
    MyAdapter adapter;
    DayAdapter weekAdapter;
    TaskAdapter taskAdapter;
    TextView tvMM;
    ImageButton monthBefore, monthAfter;



    ArrayList<ItemData> itemArr = new ArrayList<>();
    ArrayList<String> weekArr = new ArrayList<>();

    Calendar cal = Calendar.getInstance();


    // calendar
    int currMM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = findViewById(R.id.gv);
        gridDay = findViewById(R.id.dd);
        tvMM = findViewById(R.id.tv_mm);
        monthBefore = findViewById(R.id.month_before);
        monthAfter = findViewById(R.id.month_after);

        init();         // SQL

        monthBefore.setOnClickListener(this);
        monthAfter.setOnClickListener(this);


        weekArr.add("일");
        weekArr.add("월");
        weekArr.add("화");
        weekArr.add("수");
        weekArr.add("목");
        weekArr.add("금");
        weekArr.add("토");

        weekAdapter = new DayAdapter(this, weekArr);
        gridDay.setAdapter(weekAdapter);

        adapter = new MyAdapter(this);
        gv.setAdapter(adapter);

        tvMM.setText((cal.get(Calendar.MONTH) + 1) + "월");
        setCal();

        gv.setOnItemClickListener(this);
    }

    int preCount = 0;

    private void setCal() {
        cal.set(Calendar.DATE, 1);
        int yoil = cal.get(Calendar.DAY_OF_WEEK); // 7
        int dis = yoil - 1; // 6
        cal.add(Calendar.MONTH, -1);
        int max = cal.getActualMaximum(Calendar.DATE);

        preCount = dis;
        for (int i = 0; i < dis; i++) {
            itemArr.add(0, new ItemData(String.valueOf(max)));
            max--;
        }

        cal.add(Calendar.MONTH, 1);
        max = cal.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < max; i++) {
            itemArr.add(new ItemData(String.valueOf(i + 1)));
        }

        cal.set(Calendar.DATE, max);
        yoil = cal.get(Calendar.DAY_OF_WEEK);
        dis = 7 - yoil;
        for (int i = 0; i < dis; i++) {
            itemArr.add(new ItemData(String.valueOf(i + 1)));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.month_before:
                cal.add(Calendar.MONTH, -1);
                tvMM.setText((cal.get(Calendar.MONTH) + 1) + "월");
                itemArr.clear();
                setCal();
                break;
            case R.id.month_after:
                cal.add(Calendar.MONTH, 1);
                tvMM.setText((cal.get(Calendar.MONTH) + 1) + "월");
                itemArr.clear();
                setCal();
                break;
        }
    }



    /// - SQLite
    private void init() {
        db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS tasks("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "year INTEGER, "
                + "month INTEGER, "
                + "date  INTEGER, "
                + "day   TEXT, "
                + "task  TEXT" + ");");
    }


    TextView tvDate;
    EditText etText;
    ImageButton addText;
    ListView lvTask;
    private void showDial(final myDate currentDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = lnf.inflate(R.layout.activity_popup, null, false);
        tvDate = v.findViewById(R.id.tv_date);
        etText = v.findViewById(R.id.et_text);
        addText = v.findViewById(R.id.add_text);
        lvTask = v.findViewById(R.id.lv_task);

        setupTaskList(currentDate);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this,"할 일을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    String data = currentDate.yy + ", " + currentDate.mm + ", " + currentDate.dd + ", '" + currentDate.day + "', '" + etText.getText().toString() + "'";
                    String exec = "INSERT INTO tasks (year, month, date, day, task) VALUES (" + data + ")";
                    db.execSQL(exec);
                    Toast.makeText(MainActivity.this, "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                setupTaskList(currentDate);
            }
        });
        tvDate.setText(currentDate.toString());
        builder.setView(v);
        builder.create().show();

    }


    private void setupTaskList(myDate currentDate) {
        ArrayList<String> arr = new ArrayList<>();
        arr.clear();

        String condition = "year = " + currentDate.yy + " AND month = " + currentDate.mm + " AND date = " + currentDate.dd;
        Cursor c = db.rawQuery("SELECT * FROM tasks WHERE (" + condition + ")", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            arr.add(0, c.getString((5)));
            c.moveToNext();
        }
        c.close();

        taskAdapter = new TaskAdapter(this, arr, currentDate);
        lvTask.setAdapter(taskAdapter);

        taskAdapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (!(position < preCount || cal.getActualMaximum(Calendar.DATE) + preCount <= position)) {
            int dd = position - preCount + 1;
            int mm = cal.get(Calendar.MONTH);
            int yy = cal.get(Calendar.YEAR);
            cal.set(yy, mm, dd);
            String[] dayArr = {"일", "월", "화", "수", "목", "금", "토"};
            int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
            myDate currentDate = new myDate(yy, mm + 1, dd, dayArr[day]);

            showDial(currentDate);
        }

    }


    ///- Adapter
    class ItemHolder {
        TextView tvHolder;
    }

    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item, itemArr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if (convertView == null) {
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ItemHolder();
                viewHolder.tvHolder = convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemHolder) convertView.getTag();
            }

            viewHolder.tvHolder.setText(itemArr.get(position).str);

            if (position < preCount) {
                viewHolder.tvHolder.setTextColor(Color.GRAY);
            } else if (cal.getActualMaximum(Calendar.DATE) + preCount <= position) {
                viewHolder.tvHolder.setTextColor(Color.GRAY);
            } else if (position % 7 == 0) {
                viewHolder.tvHolder.setTextColor(Color.RED);
            } else if (position % 7 == 6) {
                viewHolder.tvHolder.setTextColor(Color.BLUE);
            } else {
                viewHolder.tvHolder.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}