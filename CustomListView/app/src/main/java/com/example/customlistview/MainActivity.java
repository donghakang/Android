package com.real.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customlistview.ItemData;
import com.example.customlistview.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     *  1. item 위한 레이아웃을 만든다
     *  2. item 위한 클래스를 만든다
     *  3. item 위한 데이터 클래스 만든다
     *  4. 어레이 리스트를 만든다
     *  5. listView를 만든다
     *  6. 아답터 클래스 복붙하기
     *  7. 아답터 빨간 부분 수정(대부분 대문자는 import 하면 됨 holder 제외)
     *  8. 이전 과 동일
     */

    ListView lv;
    MyAdapter adapter;
    ArrayList<ItemData> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.list_view);


        adapter = new MyAdapter(this);
        lv.setAdapter(adapter);
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
            super(context, R.layout.item, arr);
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

                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvName = convertView.findViewById(R.id.tv_name);
                viewHolder.tvDescription = convertView.findViewById(R.id.tv_description);
                viewHolder.imageVilians = convertView.findViewById(R.id.image_villains);


                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ItemHolder)convertView.getTag();
            }

            viewHolder.tvName.setText(arr.get(position).name);
            viewHolder.tvDescription.setText(arr.get(position).description);
            viewHolder.imageVilians.setImageResource(arr.get(position).image);

            return convertView;
        }
    }
}