package com.real.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    TextView tv;

    public FragmentA() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frag1,container,false);
        tv = layout.findViewById(R.id.tv);

        Toast.makeText(getActivity(), "dddd", Toast.LENGTH_SHORT).show();

        ((MainActivity) getActivity()).changeScr(MainActivity.FRAGMENT_B);
//        ((DetailActivity)getActivity()).changeScr22222();  //에러

        return layout;
    }

    public void aaa(){

    }
}
