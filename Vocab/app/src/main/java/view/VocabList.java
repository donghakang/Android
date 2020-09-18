package view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.vocab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import control.JSON;

public class VocabList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> eng;
    private final ArrayList<String> kor;
    private final ArrayList<Integer> level;


    public VocabList(Activity context, ArrayList<String> eng, ArrayList<String> kor, ArrayList<Integer> level ) {
        super(context, R.layout.vocab_list, eng);
        this.context = context;

        this.eng = eng;
        this.kor = kor;
        this.level = level;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.vocab_list, null, true);

        TextView tvEng = rowView.findViewById(R.id.tv_english);
        TextView tvKor = rowView.findViewById(R.id.tv_korean);

        ImageView lv1 = rowView.findViewById(R.id.level1);
        ImageView lv2 = rowView.findViewById(R.id.level2);
        ImageView lv3 = rowView.findViewById(R.id.level3);
        ImageView lv4 = rowView.findViewById(R.id.level4);
        ImageView lv5 = rowView.findViewById(R.id.level5);


        tvEng.setText(eng.get(position));
        tvKor.setText(kor.get(position));

        int lv = level.get(position);

        switch (lv) {
            case 0:
                lv1.setBackgroundResource(R.drawable.circle_x_level);
                lv2.setBackgroundResource(R.drawable.circle_x_level);
                lv3.setBackgroundResource(R.drawable.circle_x_level);
                lv4.setBackgroundResource(R.drawable.circle_x_level);
                lv5.setBackgroundResource(R.drawable.circle_x_level);
                break;
            case 1:
                lv1.setBackgroundResource(R.drawable.circle_level);
                lv2.setBackgroundResource(R.drawable.circle_x_level);
                lv3.setBackgroundResource(R.drawable.circle_x_level);
                lv4.setBackgroundResource(R.drawable.circle_x_level);
                lv5.setBackgroundResource(R.drawable.circle_x_level);
                break;
            case 2:
                lv1.setBackgroundResource(R.drawable.circle_level);
                lv2.setBackgroundResource(R.drawable.circle_level);
                lv3.setBackgroundResource(R.drawable.circle_x_level);
                lv4.setBackgroundResource(R.drawable.circle_x_level);
                lv5.setBackgroundResource(R.drawable.circle_x_level);
                break;
            case 3:
                lv1.setBackgroundResource(R.drawable.circle_level);
                lv2.setBackgroundResource(R.drawable.circle_level);
                lv3.setBackgroundResource(R.drawable.circle_level);
                lv4.setBackgroundResource(R.drawable.circle_x_level);
                lv5.setBackgroundResource(R.drawable.circle_x_level);
                break;
            case 4:
                lv1.setBackgroundResource(R.drawable.circle_level);
                lv2.setBackgroundResource(R.drawable.circle_level);
                lv3.setBackgroundResource(R.drawable.circle_level);
                lv4.setBackgroundResource(R.drawable.circle_level);
                lv5.setBackgroundResource(R.drawable.circle_x_level);
                break;
            case 5:
                lv1.setBackgroundResource(R.drawable.circle_level);
                lv2.setBackgroundResource(R.drawable.circle_level);
                lv3.setBackgroundResource(R.drawable.circle_level);
                lv4.setBackgroundResource(R.drawable.circle_level);
                lv5.setBackgroundResource(R.drawable.circle_level);
                break;
            default:
                break;
        }


        return rowView;
    }
}
