package com.example.intent.control;

import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import org.w3c.dom.Text;

public class AnonymousTextView {
    public TextView tv;
    private String ch;

    public AnonymousTextView(String ch) {
        this.ch = ch;
    }

    public void setupText() {
        this.tv.setGravity(Gravity.CENTER);
        this.tv.setTextSize(40);
        this.tv.setText("_");

        this.tv.setTypeface(Typeface.create("casual", Typeface.BOLD));

    }

    public void setupWeight() {
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) this.tv.getLayoutParams();
        try {
            llParams.weight = 1.0f;
            this.tv.setLayoutParams(llParams);
        } catch (NullPointerException e) {
            Log.d("NULL POINTER", llParams.toString() + " NULL NULL ");
        }
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getCh() {
        return this.ch;
    }


}
