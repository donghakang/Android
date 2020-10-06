package com.example.todocalendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DayAdapter extends ArrayAdapter {



    LayoutInflater lnf;
    ArrayList<String> arr = new ArrayList<String>();

    public DayAdapter(Activity context, ArrayList<String> arr) {
        super(context, R.layout.item, arr);
        this.arr = arr;
        lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    class DayHolder {
        TextView tvHolder;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        DayAdapter.DayHolder viewHolder;
        if (convertView == null) {
            convertView = lnf.inflate(R.layout.item, parent, false);
            viewHolder = new DayHolder();
            viewHolder.tvHolder = convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DayAdapter.DayHolder) convertView.getTag();
        }

        viewHolder.tvHolder.setText(arr.get(position));
        viewHolder.tvHolder.setTextSize(12);
        if (position % 7 == 0) {
            viewHolder.tvHolder.setTextColor(Color.RED);
        } else if (position % 7 == 6) {
            viewHolder.tvHolder.setTextColor(Color.BLUE);
        } else {
            viewHolder.tvHolder.setTextColor(Color.BLACK);
        }

        return convertView;
    }

}

