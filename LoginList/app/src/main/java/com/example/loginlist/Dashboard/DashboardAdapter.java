package com.example.loginlist.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loginlist.R;

import java.util.ArrayList;

public class DashboardAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    ArrayList<Dashboard> arr;

    public DashboardAdapter (Activity context, ArrayList<Dashboard> arr) {
        super(context, R.layout.dashboard_list, arr);
        this.arr = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder viewHolder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.dashboard_list, parent, false);
            viewHolder = new ItemHolder();

            viewHolder.tvIdx = convertView.findViewById(R.id.tv_idx);
            viewHolder.tvDashTitle = convertView.findViewById(R.id.tv_dash_title);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ItemHolder) convertView.getTag();
        }

        viewHolder.tvIdx.setText(arr.get(position).idx + "");
        viewHolder.tvDashTitle.setText(arr.get(position).title);
        viewHolder.tvDate.setText(arr.get(position).wdate);

        return convertView;
    }

    class ItemHolder {
        TextView tvIdx;
        TextView tvDashTitle;
        TextView tvDate;
    }
}
