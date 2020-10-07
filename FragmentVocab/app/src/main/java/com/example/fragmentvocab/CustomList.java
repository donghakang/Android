package com.example.fragmentvocab;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter {
    ArrayList<String> engArr;
    ArrayList<String> korArr;

    LayoutInflater inflater;

    public CustomList(Activity context, ArrayList<String> engArr, ArrayList<String> korArr) {
        super(context, R.layout.vocab_list, engArr);
        this.engArr = engArr;
        this.korArr = korArr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return engArr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return engArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder viewHolder;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.vocab_list, parent, false);
            viewHolder = new ItemHolder();

            viewHolder.tvNumber = convertView.findViewById(R.id.tv_number);
            viewHolder.tvEng = convertView.findViewById(R.id.tv_english);
            viewHolder.tvKor = convertView.findViewById(R.id.tv_korean);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ItemHolder) convertView.getTag();
        }

        viewHolder.tvNumber.setText((position + 1) + "");
        viewHolder.tvEng.setText(engArr.get(position));
        viewHolder.tvKor.setText(korArr.get(position));

        return convertView;
    }

    class ItemHolder {
        TextView tvNumber;
        TextView tvEng;
        TextView tvKor;

    }
}
