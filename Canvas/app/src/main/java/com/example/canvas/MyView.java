package com.example.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    Paint p;


    int posX = 0;
    int posY = 0;

    boolean isRight = true;
    boolean isDown = true;

    public MyView(Context context) {
        // 자바에서 올리면 실행
        super(context);
        Log.d("dddd", "생성자 1");
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        // xml에서 올리면 실행
        super(context, attrs);
        Log.d("dddd", "생성자 2");
        p = new Paint();
        new MyThread().start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.RED);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);

        p.setColor(Color.WHITE);
        canvas.drawCircle(posX, posY, 20, p);

        if (posX > getWidth()) isRight = false;
        if (posX < 0)          isRight = true;
        if (posY < 0)          isDown = true;
        if (posY > getHeight()) isDown = false;

        if (isRight) {
            posX += 5;
            if (isDown) {
                posY += 5;
            } else {
                posY -= 5;
            }
        } else {
            posX -= 5;
            if (isDown) {
                posY += 5;
            } else {
                posY -= 5;
            }
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(true) {
                invalidate();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

}


