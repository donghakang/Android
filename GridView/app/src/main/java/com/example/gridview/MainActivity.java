package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    GridView gv;
    MyAdapter adapter;
    ArrayList<ItemData> itemArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv = findViewById(R.id.gv);

        adapter = new MyAdapter(this);
        gv.setAdapter(adapter);

//        for (int i = 0; i < 100; i++) {
//            arr.add(new ItemData("가"));
//            arr.add(new ItemData("나"));
//            arr.add(new ItemData("다"));
//            arr.add(new ItemData("라"));
//            arr.add(new ItemData("마"));
//            arr.add(new ItemData("바"));
//        }

        setCal();
    }

    int preCount = 0;

    private void setCal() {
        Calendar cal = Calendar.getInstance();

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