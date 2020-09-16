package com.example.intent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VocabList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> eng;
    private final ArrayList<String> kor;
    public VocabList(Activity context, ArrayList<String> eng, ArrayList<String> kor) {
        super(context, R.layout.list_single, eng);
        this.context = context;
        this.eng = eng;
        this.kor = kor;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtEng = (TextView) rowView.findViewById(R.id.id_eng);
        TextView txtKor = (TextView) rowView.findViewById(R.id.id_kor);

        txtEng.setText(eng.get(position));
        txtKor.setText(kor.get(position));

        return rowView;
    }
}
