package com.example.loginlist.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.loginlist.R;

import java.util.ArrayList;

public class ReplyAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    ArrayList<Reply> arr;

    public ReplyAdapter (Activity context, ArrayList<Reply> arr) {
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
        ReplyAdapter.ReplyItemHolder viewHolder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.response_list, parent, false);
            viewHolder = new ReplyItemHolder();

            viewHolder.tvWriter = convertView.findViewById(R.id.tv_replier);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_reply);
//            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ReplyAdapter.ReplyItemHolder) convertView.getTag();
        }

        viewHolder.tvWriter.setText(arr.get(position).writer);
        viewHolder.tvContent.setText(arr.get(position).content);

        return convertView;
    }

    class ReplyItemHolder {
        TextView tvWriter;
        TextView tvContent;
//        TextView tvDate;
    }
}
