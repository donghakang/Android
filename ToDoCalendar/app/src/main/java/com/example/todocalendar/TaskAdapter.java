package com.example.todocalendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    ArrayList<String> arr;
    myDate currentDate;

    // create separate class to set elements
    class TaskItemHolder {
        TextView task;
        ImageButton btnDelete;
    }

    public TaskAdapter (Activity context, ArrayList<String> arr, myDate currentDate) {
        super(context, R.layout.task_item, arr);
        this.arr = arr;
        this.currentDate = currentDate;
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

    /// - MOST IMPORTANT
    public View getView(final int position, View convertView, ViewGroup parent) {
        TaskAdapter.TaskItemHolder viewHolder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.task_item, parent, false);
            viewHolder = new TaskItemHolder();

            // call the convertView's elements (previously defined in xml file)
            viewHolder.task = convertView.findViewById(R.id.task);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (TaskItemHolder) convertView.getTag();
        }

        viewHolder.task.setText(arr.get(position));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}


