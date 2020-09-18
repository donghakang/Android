package com.example.pastblockbuster;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    ArrayList<Movie> arr;

    public MovieAdapter (Activity context, ArrayList<Movie> arr) {
        super(context, R.layout.movie_list, arr);
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

            convertView = inflater.inflate(R.layout.movie_list, parent, false);
            viewHolder = new ItemHolder();

            viewHolder.tvRank = convertView.findViewById(R.id.tv_rank);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.releaseDate = convertView.findViewById(R.id.tv_releasedate);
            viewHolder.audience = convertView.findViewById(R.id.tv_audience);
            viewHolder.cumulative = convertView.findViewById(R.id.tv_cumulative);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ItemHolder) convertView.getTag();
        }

        viewHolder.tvRank.setText((position + 1) + "");
        viewHolder.tvTitle.setText(arr.get(position).name);
        viewHolder.releaseDate.setText(arr.get(position).date);
        viewHolder.audience.setText(arr.get(position).aud);
        viewHolder.cumulative.setText(arr.get(position).aud_cum);

        return convertView;
    }

    class ItemHolder {
        TextView tvRank;
        TextView tvTitle;
        TextView releaseDate;
        TextView audience;
        TextView cumulative;
    }
}
