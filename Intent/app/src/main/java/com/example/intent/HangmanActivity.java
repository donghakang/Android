package com.example.intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class HangmanActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO;
    Button btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ;

    ImageView ivImg;
    LinearLayout llAnswer, llKeyboard;

    TextView tvGameover;
    Button btnReplay;

    int level = 1;
    boolean wordFound = false;

    String answer = "";
    ArrayList<AnonymousTextView> answerArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        buttonSetup();
        ivImg = findViewById(R.id.iv_img);
        llAnswer = findViewById(R.id.ll_answer);
        llKeyboard = findViewById(R.id.ll_keyboard);

        // No Game
        btnReplay = findViewById(R.id.btn_replay);
        tvGameover = findViewById(R.id.tv_gameover);

        btnReplay.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setup();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_replay) {
            replay();
            setup();
        } else {
            switch (v.getId()) {
                case R.id.btn_a:
                    btnA.setText("");
                    btnA.setEnabled(false);
                    guess("A");
                    break;
                case R.id.btn_b:
                    btnB.setText("");
                    btnB.setEnabled(false);
                    guess("B");
                    break;
                case R.id.btn_c:
                    btnC.setText("");
                    btnC.setEnabled(false);
                    guess("C");
                    break;
                case R.id.btn_d:
                    btnD.setText("");
                    btnD.setEnabled(false);
                    guess("D");
                    break;
                case R.id.btn_e:
                    btnE.setText("");
                    btnE.setEnabled(false);
                    guess("E");
                    break;
                case R.id.btn_f:
                    btnF.setText("");
                    btnF.setEnabled(false);
                    guess("F");
                    break;
                case R.id.btn_g:
                    btnG.setText("");
                    btnG.setEnabled(false);
                    guess("G");
                    break;
                case R.id.btn_h:
                    btnH.setText("");
                    btnH.setEnabled(false);
                    guess("H");
                    break;
                case R.id.btn_i:
                    btnI.setText("");
                    btnI.setEnabled(false);
                    guess("I");
                    break;
                case R.id.btn_j:
                    btnJ.setText("");
                    btnJ.setEnabled(false);
                    guess("J");
                    break;
                case R.id.btn_k:
                    btnK.setText("");
                    btnK.setEnabled(false);
                    guess("K");
                    break;
                case R.id.btn_l:
                    btnL.setText("");
                    btnL.setEnabled(false);
                    guess("L");
                    break;
                case R.id.btn_m:
                    btnM.setText("");
                    btnM.setEnabled(false);
                    guess("M");
                    break;
                case R.id.btn_n:
                    btnN.setText("");
                    btnN.setEnabled(false);
                    guess("N");
                    break;
                case R.id.btn_o:
                    btnO.setText("");
                    btnO.setEnabled(false);
                    guess("O");
                    break;
                case R.id.btn_p:
                    btnP.setText("");
                    btnP.setEnabled(false);
                    guess("P");
                    break;
                case R.id.btn_q:
                    btnQ.setText("");
                    btnQ.setEnabled(false);
                    guess("Q");
                    break;
                case R.id.btn_r:
                    btnR.setText("");
                    btnR.setEnabled(false);
                    guess("R");
                    break;
                case R.id.btn_s:
                    btnS.setText("");
                    btnS.setEnabled(false);
                    guess("S");
                    break;
                case R.id.btn_t:
                    btnT.setText("");
                    btnT.setEnabled(false);
                    guess("T");
                    break;
                case R.id.btn_u:
                    btnU.setText("");
                    btnU.setEnabled(false);
                    guess("U");
                    break;
                case R.id.btn_v:
                    btnV.setText("");
                    btnV.setEnabled(false);
                    guess("V");
                    break;
                case R.id.btn_w:
                    btnW.setText("");
                    btnW.setEnabled(false);
                    guess("W");
                    break;
                case R.id.btn_x:
                    btnX.setText("");
                    btnX.setEnabled(false);
                    guess("X");
                    break;
                case R.id.btn_y:
                    btnY.setText("");
                    btnY.setEnabled(false);
                    guess("Y");
                    break;
                case R.id.btn_z:
                    btnZ.setText("");
                    btnZ.setEnabled(false);
                    guess("Z");
                    break;
                default:
                    break;
            }

            level += 1;
            Log.d("HEO", level + "");
            if (level >= 10) {
                // 게임 오버
                gameOver();
            }
            if (isDone()) {
                // 승리
                gameOver();
            }

            if (wordFound) {
                Toast.makeText(this, "단어가 있습니다", Toast.LENGTH_SHORT).show();
            } else {
                if (level != 10) {
                    Toast.makeText(this, 10 - level + "회 남았습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            imageUpdate();
            wordFound = false;
        }

    }


    private void setup() {
        // view setting
        tvGameover.setVisibility(TextView.INVISIBLE);
        btnReplay.setVisibility(Button.INVISIBLE);

        llKeyboard.setVisibility(LinearLayout.VISIBLE);
        llAnswer.setVisibility(LinearLayout.VISIBLE);

        // game setup
        level = 1;
        imageUpdate();

        // word setup
        answer = Storage.vocaArr.get(new Random().nextInt(Storage.vocaArr.size())).eng;
        answer = answer.toUpperCase();

        answerArr = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            AnonymousTextView atv = new AnonymousTextView(Character.toString(answer.charAt(i)));
            atv.tv = new TextView(this);
            atv.setupText();
            llAnswer.addView(atv.tv);
            atv.setupWeight();

            answerArr.add(atv);
        }
    }


    private void replay() {
        btnA.setText("A");  btnA.setEnabled(true);
        btnB.setText("B");  btnB.setEnabled(true);
        btnC.setText("C");  btnC.setEnabled(true);
        btnD.setText("D");  btnD.setEnabled(true);
        btnE.setText("E");  btnE.setEnabled(true);
        btnF.setText("F");  btnF.setEnabled(true);
        btnG.setText("G");  btnG.setEnabled(true);
        btnH.setText("H");  btnH.setEnabled(true);
        btnI.setText("I");  btnI.setEnabled(true);
        btnJ.setText("J");  btnJ.setEnabled(true);
        btnK.setText("K");  btnK.setEnabled(true);
        btnL.setText("L");  btnL.setEnabled(true);
        btnM.setText("M");  btnM.setEnabled(true);
        btnN.setText("N");  btnN.setEnabled(true);
        btnO.setText("O");  btnO.setEnabled(true);
        btnP.setText("P");  btnP.setEnabled(true);
        btnQ.setText("Q");  btnQ.setEnabled(true);
        btnR.setText("R");  btnR.setEnabled(true);
        btnS.setText("S");  btnS.setEnabled(true);
        btnT.setText("T");  btnT.setEnabled(true);
        btnU.setText("U");  btnU.setEnabled(true);
        btnV.setText("V");  btnV.setEnabled(true);
        btnW.setText("W");  btnW.setEnabled(true);
        btnX.setText("X");  btnX.setEnabled(true);
        btnY.setText("Y");  btnY.setEnabled(true);
        btnZ.setText("Z");  btnZ.setEnabled(true);

        llAnswer.removeAllViews();
    }


    private boolean isDone() {
        for (int i = 0; i < answerArr.size(); i++) {
            if (answerArr.get(i).tv.getText().toString().equals("_")) {
                return false;
            }
        }
        return true;
    }

    private void gameOver() {
//        if (isDone()) {
//            tvGameover.setText("VICTORY");
//        }
//        tvGameover.setVisibility(TextView.VISIBLE);
//        btnReplay.setVisibility(Button.VISIBLE);
//
//        llKeyboard.setVisibility(LinearLayout.INVISIBLE);
//        llAnswer.setVisibility(LinearLayout.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (isDone()) {
            builder.setTitle("정답입니다!");
        } else {
            builder.setTitle("오답입니다.." + answer);
        }
        builder.setMessage("다시 플레이 하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                replay();
                setup();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.create().show();
    }

    private void guess(String ch) {
        for (int i = 0; i < answerArr.size(); i++) {
            if (answerArr.get(i).getCh().equals(ch)) {
                // 만약 맞으면 글짜를 _ 에서 실제 단어로 바꾼다.
                answerArr.get(i).tv.setText(ch);
                level--;
                imageUpdate();
                wordFound = true;
            }
        }
    }


    private void imageUpdate() {
        switch (level) {
            case 1:
                ivImg.setImageResource(R.drawable.level1);
                break;
            case 2:
                ivImg.setImageResource(R.drawable.level2);
                break;
            case 3:
                ivImg.setImageResource(R.drawable.level3);
                break;
            case 4:
                ivImg.setImageResource(R.drawable.level4);
                break;
            case 5:
                ivImg.setImageResource(R.drawable.level5);
                break;
            case 6:
                ivImg.setImageResource(R.drawable.level6);
                break;
            case 7:
                ivImg.setImageResource(R.drawable.level7);
                break;
            case 8:
                ivImg.setImageResource(R.drawable.level8);
                break;
            case 9:
                ivImg.setImageResource(R.drawable.level9);
                break;
            case 10:
                ivImg.setImageResource(R.drawable.level10);
                break;
            default:
                break;
        }
    }


    private void buttonSetup() {
        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);
        btnD = findViewById(R.id.btn_d);
        btnE = findViewById(R.id.btn_e);
        btnF = findViewById(R.id.btn_f);
        btnG = findViewById(R.id.btn_g);
        btnH = findViewById(R.id.btn_h);
        btnI = findViewById(R.id.btn_i);
        btnJ = findViewById(R.id.btn_j);
        btnK = findViewById(R.id.btn_k);
        btnL = findViewById(R.id.btn_l);
        btnM = findViewById(R.id.btn_m);
        btnN = findViewById(R.id.btn_n);
        btnO = findViewById(R.id.btn_o);
        btnP = findViewById(R.id.btn_p);
        btnQ = findViewById(R.id.btn_q);
        btnR = findViewById(R.id.btn_r);
        btnS = findViewById(R.id.btn_s);
        btnT = findViewById(R.id.btn_t);
        btnU = findViewById(R.id.btn_u);
        btnV = findViewById(R.id.btn_v);
        btnW = findViewById(R.id.btn_w);
        btnX = findViewById(R.id.btn_x);
        btnY = findViewById(R.id.btn_y);
        btnZ = findViewById(R.id.btn_z);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnE.setOnClickListener(this);
        btnF.setOnClickListener(this);
        btnG.setOnClickListener(this);
        btnH.setOnClickListener(this);
        btnI.setOnClickListener(this);
        btnJ.setOnClickListener(this);
        btnK.setOnClickListener(this);
        btnL.setOnClickListener(this);
        btnM.setOnClickListener(this);
        btnN.setOnClickListener(this);
        btnO.setOnClickListener(this);
        btnP.setOnClickListener(this);
        btnQ.setOnClickListener(this);
        btnR.setOnClickListener(this);
        btnS.setOnClickListener(this);
        btnT.setOnClickListener(this);
        btnU.setOnClickListener(this);
        btnV.setOnClickListener(this);
        btnW.setOnClickListener(this);
        btnX.setOnClickListener(this);
        btnY.setOnClickListener(this);
        btnZ.setOnClickListener(this);
    }


}