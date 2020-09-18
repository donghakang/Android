package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lv;
    MyAdapter adapter;
    public static ArrayList<Contact> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.list_view);


        arr.add(new Contact("안성은", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("강동하", "남", "010-2902-8330", "dkang0602@gmail.com", R.drawable.ryan));
        arr.add(new Contact("박지성", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("신상철", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("오현화", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("이동수", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이홍은", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("한동욱", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김동현", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이한솔", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("신동재", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("고은비", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("김경동", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이해민", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("고정환", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("장서정", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("박진혁", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이강원", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김석중", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김재혁", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("서민지", "여", "010-0000-0000", "gmail@gmail.com", R.drawable.apeach));
        arr.add(new Contact("이성찬", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("남궁민", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김현성", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이대환", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김경민", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("임동진", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("김건우", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("박지성", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("신상철", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));
        arr.add(new Contact("이동수", "남", "010-0000-0000", "gmail@gmail.com", R.drawable.ryan));

        adapter = new MyAdapter(this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, com.example.contacts.ContactActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    class ItemHolder {
        ImageView img;
        TextView tvName;
        TextView tvSex;
        TextView tvNumber;
        TextView tvEmail;
    }

    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.contact_list, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                convertView = lnf.inflate(R.layout.contact_list, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvName = convertView.findViewById(R.id.tv_name);
                viewHolder.tvSex = convertView.findViewById(R.id.tv_sex);
                viewHolder.tvNumber = convertView.findViewById(R.id.tv_number);
                viewHolder.tvEmail = convertView.findViewById(R.id.tv_email);
                viewHolder.img = convertView.findViewById(R.id.image_contact);


                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder)convertView.getTag();
            }

            viewHolder.tvName.setText(arr.get(position).name);
            viewHolder.tvSex.setText(arr.get(position).sex);
            viewHolder.tvNumber.setText(arr.get(position).number);
            viewHolder.tvEmail.setText(arr.get(position).email);
            viewHolder.img.setImageResource(arr.get(position).image);

            return convertView;
        }
    }



}