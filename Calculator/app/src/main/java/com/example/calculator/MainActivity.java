package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvEquation, tvAnswer;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAdd, btnSubtract, btnMultiply, btnDivision, btnClear, btnEqual;

    String eq = "";
    double answer = 0.0;

    boolean isCh = false;
    boolean isStart = true;          // 맨 처음에, '-' 와 숫자 외에는 다른 기호들을 누를 수 없다.


    private double calculate (String eq) {
        ArrayList <Character> ch = new ArrayList<>();
        ArrayList <Double> num   = new ArrayList<>();

        double answer = 0.0;

        int countAdd = 0;
        int countSub = 0;
        int countMul = 0;
        int countDiv = 0;
        int index;



        char lastCh = ' ';
        if (eq.endsWith("+") || eq.endsWith("-") || eq.endsWith("/") || eq.endsWith("*")) {
            lastCh = eq.charAt(eq.length() - 1);
            eq = eq.substring(0, eq.length() - 1);
        }

        int start = 0;
        for (int i = 0; i < eq.length(); i ++) {
            // 맨 처음에는 마이너스가 올수 있다.
            if (i != 0) {
                if (eq.charAt(i) == '+' || eq.charAt(i) == '-' || eq.charAt(i) == '*' || eq.charAt(i) == '/') {
                    if (eq.charAt(i) == '+') countAdd += 1;
                    if (eq.charAt(i) == '-') countSub += 1;
                    if (eq.charAt(i) == '*') countMul += 1;
                    if (eq.charAt(i) == '/') countDiv += 1;

                    num.add(Double.parseDouble(eq.substring(start, i)));
                    ch.add(eq.charAt(i));
                    start = i + 1;
                }
            }
        }
        num.add(Double.parseDouble(eq.substring(start, eq.length())));


        for (int i = 0; i < countMul; i ++) {
            if (ch.contains('*')) {
                index = ch.indexOf('*');
                ch.remove(index);

                answer = num.get(index) * num.get(index + 1);
                num.remove(index);
                num.set(index, answer);
            }
        }
        for (int i = 0; i < countDiv; i ++) {
            if (ch.contains('/')) {
                index = ch.indexOf('/');
                ch.remove(index);

                answer = num.get(index) / num.get(index + 1);
                num.remove(index);
                num.set(index, answer);
            }
        }
        for (int i = 0; i < countAdd; i ++) {
            if (ch.contains('+')) {
                index = ch.indexOf('+');
                ch.remove(index);

                answer = num.get(index) + num.get(index + 1);
                num.remove(index);
                num.set(index, answer);
            }
        }
        for (int i = 0; i < countSub; i ++) {
            if (ch.contains('-')) {
                index = ch.indexOf('-');
                ch.remove(index);

                answer = num.get(index) - num.get(index + 1);
                num.remove(index);
                num.set(index, answer);
            }
        }

        if (lastCh != ' ') {
            if (lastCh == '+') answer += answer;
            else if (lastCh == '/') answer /= answer;
            else if (lastCh == '-') answer -= answer;
            else if (lastCh == '*') answer *= answer;
        }

        Log.d("asdf", String.valueOf(answer));
        return answer;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Text View
        tvEquation = findViewById(R.id.tv_equation);
        tvAnswer = findViewById(R.id.tv_answer);

        // Buttons
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);

        btnAdd = findViewById(R.id.btn_add);
        btnSubtract = findViewById(R.id.btn_subtract);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDivision = findViewById(R.id.btn_division);
        btnEqual = findViewById(R.id.btn_equal);
        btnClear = findViewById(R.id.btn_clear);

        // button set click listener
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnClear.setOnClickListener(this);


        tvAnswer.setText(String.valueOf(answer));
        tvEquation.setText(eq);
    }

    @Override
    public void onClick(View v) {
        int num = 0;
        String tmpNum = "";
        if (v.getId() == R.id.btn_0) {
            if (!isCh)    isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(0);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_1) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(1);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_2) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(2);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_3) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(3);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_4) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(4);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_5) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(5);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_6) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(6);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_7) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(7);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_8) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(8);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_9) {
            if (!isCh)   isCh = true;
            if (!isStart) isStart = true;
            btnClear.setText("C");

            eq += String.valueOf(9);
            tvEquation.setText(eq);
        } else if (v.getId() == R.id.btn_add) {                     // equation
            if (isCh && isStart) {
                eq += "+";
                tvEquation.setText(eq);
                isCh = false;
            }
        } else if (v.getId() == R.id.btn_subtract) {
            if (!isStart) {
                eq += "-";
                tvEquation.setText(eq);
                isStart = true;
            }
            if (isCh) {
                eq += "-";
                tvEquation.setText(eq);
                isCh = false;
            }
        } else if (v.getId() == R.id.btn_multiply) {
            if (isCh && isStart) {
                eq += "*";
                tvEquation.setText(eq);
                isCh = false;
            }
        } else if (v.getId() == R.id.btn_division) {
            if (isCh && isStart) {
                eq += "/";
                tvEquation.setText(eq);
                isCh = false;
            }
        } else if (v.getId() == R.id.btn_equal) {
            if (isCh && isStart) {
                answer = calculate(eq);
                tvAnswer.setText(String.valueOf(answer));

                eq += "=";
                tvEquation.setText(eq);

                isCh = false;
                isStart = false;
            }
        } else if (v.getId() == R.id.btn_clear) {
            if (btnClear.getText().equals("C")) {
                isCh = false;
                isStart = false;

                eq = "";
                answer = 0;
                tvEquation.setText(eq);
                tvAnswer.setText(String.valueOf(answer));
                btnClear.setText("AC");

            }
        }
    }
}